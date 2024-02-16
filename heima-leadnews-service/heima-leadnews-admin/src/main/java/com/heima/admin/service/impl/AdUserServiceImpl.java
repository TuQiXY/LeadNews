package com.heima.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.admin.mapper.AdUserMapper;
import com.heima.admin.service.AdUserService;
import com.heima.model.admin.dtos.LoginAdDto;
import com.heima.model.admin.pojos.AdUser;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.utils.common.AppJwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AdUserServiceImpl extends ServiceImpl<AdUserMapper, AdUser> implements AdUserService {
    /**
     * 管理员登录
     * @param loginAdDto
     * @return
     */
    @Override
    public ResponseResult login(LoginAdDto loginAdDto) {
        //1.校验参数
        String name = loginAdDto.getName();
        String password = loginAdDto.getPassword();
        if(StringUtils.isBlank(name) ||StringUtils.isBlank(password)){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //2.去查询数据库
        LambdaQueryWrapper<AdUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdUser::getName,name);
        AdUser adUser = this.getOne(queryWrapper);

        if(ObjectUtils.isEmpty(adUser)){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST,"管理员不存在");
        }
        //3.然后获取颜，比对密码
        String salt = adUser.getSalt();
        String pass = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        if(!pass.equals(adUser.getPassword())){
            return ResponseResult.errorResult(AppHttpCodeEnum.LOGIN_PASSWORD_ERROR);
        }
        //4.生成token
        String token = AppJwtUtil.getToken(Long.valueOf(adUser.getId()));
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        adUser.setSalt("");
        adUser.setPassword("");
        map.put("user",adUser);
        return ResponseResult.okResult(map);

    }
}
