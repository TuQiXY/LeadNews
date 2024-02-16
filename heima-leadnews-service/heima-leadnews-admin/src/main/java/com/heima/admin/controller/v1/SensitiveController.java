package com.heima.admin.controller.v1;

import com.heima.apis.wmedia.IWMediaClient;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.SensitivePageReqDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/v1/sensitive")
@Api("敏感词管理")
public class SensitiveController {

     @Resource
     private IWMediaClient iwMediaClient;

     @ApiOperation("分页条件查询敏感词")
     @PostMapping("/list")
     public ResponseResult page(@RequestBody SensitivePageReqDto sensitivePageReqDto){
         ResponseResult responseResult = iwMediaClient.pageSensitive(sensitivePageReqDto);
         if(responseResult.getCode()==200){
             return responseResult;
         }else {
             return ResponseResult.errorResult(responseResult.getCode(),"敏感词查询失败");
         }
     }



}
