package com.heima.admin.controller.v1;

import com.heima.admin.service.AdUserService;
import com.heima.model.admin.dtos.LoginAdDto;
import com.heima.model.common.dtos.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api("管理员用户登录接口")
@RestController
@RequestMapping("/login")
public class AdUserLoginController {

    @Resource
    private AdUserService adUserService;

    @ApiOperation("管理员用户登录")
    @PostMapping("/in")
    public ResponseResult login(@RequestBody LoginAdDto loginAdDto){


        return adUserService.login(loginAdDto);

    }

}
