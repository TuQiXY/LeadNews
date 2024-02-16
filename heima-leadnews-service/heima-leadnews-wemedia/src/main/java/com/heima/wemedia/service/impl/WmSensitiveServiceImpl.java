package com.heima.wemedia.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.model.common.dtos.PageResponseResult;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.SensitivePageReqDto;
import com.heima.model.wemedia.pojos.WmSensitive;
import com.heima.wemedia.mapper.WmSensitiveMapper;
import com.heima.wemedia.service.WmSensitiveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WmSensitiveServiceImpl extends ServiceImpl<WmSensitiveMapper, WmSensitive> implements WmSensitiveService {

    /**
     * 分页查询敏感词
     * @param sensitivePageReqDto
     * @return
     */
    @Override
    public ResponseResult pageList(SensitivePageReqDto sensitivePageReqDto) {

        //设置分页
        sensitivePageReqDto.checkParam();


        IPage<WmSensitive> page = new Page<>(sensitivePageReqDto.getPage(), sensitivePageReqDto.getSize());

        String name = sensitivePageReqDto.getName();
        LambdaQueryWrapper<WmSensitive> queryWrapper = new LambdaQueryWrapper<>();
        if(name != null && !"".equals(name)){
            queryWrapper.like(WmSensitive::getSensitives,name);
        }
        //根据时间倒序
        queryWrapper.orderByDesc(WmSensitive::getCreatedTime);
        this.page(page,queryWrapper);


        ResponseResult responseResult = new PageResponseResult(sensitivePageReqDto.getPage(),
                sensitivePageReqDto.getSize(),(int)page.getTotal());

        responseResult.setData(page.getRecords());

        return responseResult;

    }
}
