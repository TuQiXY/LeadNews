package com.heima.wemedia.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.WmAuditDto;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;
import com.heima.wemedia.service.WmNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news")
public class WmNewsController {


    @Autowired
    private WmNewsService wmNewsService;

    @PostMapping("/list")
    public ResponseResult findAll(@RequestBody WmNewsPageReqDto dto){
        return  wmNewsService.findAll(dto);
    }

    /**
     * 发布或者修改文章
     * @param dto
     * @return
     */
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

    /**
     *  分页条件查询自媒体文章
     * @param dto
     * @return
     */
    @PostMapping("/list_vo")
    public ResponseResult pageQuery(@RequestBody WmNewsPageReqDto dto){
        return wmNewsService.pageQuery(dto);
    }

    /**
     * 根据id查看具体的文章
     * @param id
     * @return
     */
    @GetMapping("/one_vo/{id}")
    public ResponseResult seeOne(@PathVariable("id") Integer id){
        WmNews wmNews = wmNewsService.getById(id);
        if(ObjectUtils.isEmpty(wmNews)){
            return  ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"文章不存在");
        }else{
            return ResponseResult.okResult(wmNews);
        }
    }

    /**
     * 人工审核通过
     * @param auditDto
     * @return
     */
    @Transactional
    @PostMapping("/auth_pass")
    public ResponseResult  pass(@RequestBody WmAuditDto auditDto){
        return wmNewsService.pass(auditDto);
    }

    /**
     * 人工审核失败
     * @param auditDto
     * @return
     */
    @Transactional
    @PostMapping("/auth_fail")
    public ResponseResult  fail(@RequestBody WmAuditDto auditDto){
        return wmNewsService.failPass(auditDto);
    }

}