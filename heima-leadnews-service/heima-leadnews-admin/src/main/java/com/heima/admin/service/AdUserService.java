package com.heima.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.admin.dtos.LoginAdDto;
import com.heima.model.admin.pojos.AdUser;
import com.heima.model.common.dtos.ResponseResult;

public interface AdUserService extends IService<AdUser> {
    ResponseResult login(LoginAdDto loginAdDto);
}
