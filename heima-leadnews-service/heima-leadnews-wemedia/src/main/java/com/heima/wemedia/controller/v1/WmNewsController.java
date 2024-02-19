package com.heima.wemedia.controller.v1;

import com.heima.common.constants.WemediaConstants;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.NewsAuthDto;
import com.heima.model.wemedia.dtos.WmAuditDto;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.wemedia.service.WmNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news")
@Api(tags = "文章操作")
public class WmNewsController {


    @Autowired
    private WmNewsService wmNewsService;

    @ApiOperation("自媒体查询文章列表")
    @PostMapping("/list")
    public ResponseResult findAll(@RequestBody WmNewsPageReqDto dto){
        return  wmNewsService.findAll(dto);
    }

    @ApiOperation("自媒体文章发布")
    @PostMapping("/submit")
    public ResponseResult submitNews(@RequestBody WmNewsDto dto){
        return  wmNewsService.submitNews(dto);
    }

    @GetMapping("/one/{id}")
    public ResponseResult findOne(@PathVariable("id") Integer id){
        return wmNewsService.findOne(id);
    }

    /**
     * 删除文章
     * @param id
     * @return
     */
    @GetMapping("/del_news/{id}")
    public ResponseResult deleteNews(@PathVariable Integer id){

        return wmNewsService.deleteNews(id);
    }
    @PostMapping("/down_or_up")
    public ResponseResult downOrUp(@RequestBody WmNewsDto dto){
        return wmNewsService.downOrUp(dto);
    }


    /**自媒体文章人工审核
     * 平台管理-查询文章列表
     * @param dto
     * @return
     */
    @PostMapping("/list_vo")
    public ResponseResult findList(@RequestBody NewsAuthDto dto){
        return wmNewsService.findList(dto);
    }

    /**
     * 平台管理-查询文章详情
     * @param id
     * @return
     */
    @GetMapping("/one_vo/{id}")
    public ResponseResult findWmNewsVo(@PathVariable("id") Integer id){
        return wmNewsService.findWmNewsVo(id);
    }

    /**
     * 文章审核成功
     * @param dto
     * @return
     */
    @PostMapping("/auth_pass")
    public ResponseResult authPass(@RequestBody NewsAuthDto dto){
        return wmNewsService.updateStatus(WemediaConstants.WM_NEWS_AUTH_PASS,dto);
    }

    /**
     * 文章审核失败
     * @param dto
     * @return
     */
    @PostMapping("/auth_fail")
    public ResponseResult authFail(@RequestBody NewsAuthDto dto){
        return wmNewsService.updateStatus(WemediaConstants.WM_NEWS_AUTH_FAIL,dto);
    }

}