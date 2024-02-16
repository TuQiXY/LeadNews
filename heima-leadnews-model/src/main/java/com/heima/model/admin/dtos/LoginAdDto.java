package com.heima.model.admin.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "管理员登录DTO",description = "管理员登录DTO")
public class LoginAdDto {
    /**
     * 登录用户名
     */
    @ApiModelProperty(value = "登录用户名",required = true)
    private String name;
    /**
     * 登录密码
     */
    @ApiModelProperty(value = "登录密码",required = true)
    private String password;


}
