package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.apis.article.IArticleClient;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.ChannelPageReqDto;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.wemedia.mapper.WmChannelMapper;
import com.heima.wemedia.service.WmChannelService;
import com.heima.wemedia.service.WmNewsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@Slf4j
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements WmChannelService {


    @Resource
    private IArticleClient articleClient;
    @Resource
    private WmNewsService wmNewsService;

    /**
     * 查询所有频道
     * @return
     */
    @Override
    public ResponseResult findAll() {
        return ResponseResult.okResult(list());
    }

    /**
     * 分页条件查询
     * @param channelPageReqDto
     * @return
     */
    @Override
    public ResponseResult pageQuery(ChannelPageReqDto channelPageReqDto) {

        channelPageReqDto.checkParam();

        IPage<WmChannel> page = new Page<>(channelPageReqDto.getPage(), channelPageReqDto.getSize());

        LambdaQueryWrapper<WmChannel> wrapper = new LambdaQueryWrapper<>();

        String name = channelPageReqDto.getName();
        if(!StringUtils.isBlank(name)){
            wrapper.like(WmChannel::getName,name);
        }
        //再来一个时间倒序
        wrapper.orderByDesc(WmChannel::getCreatedTime);

        this.page(page,wrapper);


        ResponseResult responseResult = new PageResponseResult(channelPageReqDto.getPage(),
                channelPageReqDto.getSize(),(int)page.getTotal());

        responseResult.setData(page.getRecords());

        return responseResult;


    }

    /**
     * 根据频道id删除频道
     * @param id
     * @return
     */
    @Override
    public ResponseResult deleteChannel(Integer id) {
       //先查询在ap文章中是否存在该频道的内容
        ResponseResult res = articleClient.getByChannelId(id);
        if(res.getCode()==200){
            //有，存在，不能删除
            return ResponseResult.errorResult(AppHttpCodeEnum.CHANNEL_ARTICLE_REFERENCE_EXIST
            ,"该频道下存在文章，不能删除");
        }
        //然后查询该库下面的自媒体文章
        LambdaQueryWrapper<WmNews> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WmNews::getChannelId,id);
        List<WmNews> list = wmNewsService.list(wrapper);
        if(!ObjectUtils.isEmpty(list)){
            return ResponseResult.errorResult(AppHttpCodeEnum.CHANNEL_WM_ARTICLE_REFERENCE_EXIST,
                    "该频道下存在自媒体文章，不能删除");
        }
        //都没有，则可以删除频道
        boolean b = this.removeById(id);
        if(b){
            return ResponseResult.okResult("删除成功!");
        }else{
            log.error("删除频道失败,{}",id);
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR,"删除频道失败!");
        }
    }

    /**
     * 新建频道
     * @param wmChannel
     * @return
     */
    @Override
    public ResponseResult addOne(WmChannel wmChannel) {
        //1.先校验参数
        if(ObjectUtils.isEmpty(wmChannel)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE,"参数不能为空");
        }
        //2.校验频道名称是否重复
        LambdaQueryWrapper<WmChannel> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WmChannel::getName,wmChannel.getName());
        WmChannel one = this.getOne(wrapper);
        if(!ObjectUtils.isEmpty(one)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST,"频道名称已存在");
        }
        //校验状态
        Boolean status = wmChannel.getStatus();
        if(ObjectUtils.isEmpty(status)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE,"状态不能为空！");
        }
        //id不能存在
        if(!ObjectUtils.isEmpty(wmChannel.getId())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE,"id不能设置值！");
        }
        //进行保存
        wmChannel.setIsDefault(true);
        wmChannel.setCreatedTime(new Date());
        boolean save = this.save(wmChannel);
        if(save){
            return ResponseResult.okResult("新增成功！");
        }else {
            log.error("新增频道失败！{}",wmChannel);
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR,"新增失败！");
        }
    }

    /**
     *  根据id修改频道
     * @param wmChannel
     * @return
     */
    @Override
    public ResponseResult updateChannel(WmChannel wmChannel) {
        //校验id
        Integer id = wmChannel.getId();
        if(ObjectUtils.isEmpty(id)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE,"id不能为空！");
        }

        //校验状态
        Boolean status = wmChannel.getStatus();
        if(ObjectUtils.isEmpty(status)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE,"状态不能为空！");
        }
        //当名称不为空的时候，去做判断
        if(wmChannel.getName()!=null){
            //然后根据名称从数据库查询该频道
            LambdaQueryWrapper<WmChannel> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(WmChannel::getName,wmChannel.getName());
            WmChannel channel = this.getOne(queryWrapper);
            //如果查出来的channel的id和传过来的id不一致，则是已经存在
            if(channel!=null && !channel.getId().equals(id)){
                return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID,"该频道是其他频道，不能修改！");
            }
        }


        //都没有问题了，然后可以进行修改
        boolean b = this.updateById(wmChannel);
        if(b){
            return ResponseResult.okResult("修改成功");
        }else{
            log.error("修改频道失败：{}",wmChannel);
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR,"修改频道失败！");
        }


    }
}
