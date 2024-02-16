package com.heima.model.user.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

/**
 * 用户审核dto
 */
@Data
public class AuditUserDto extends PageRequestDto {

    /**
     *  审核状态
     */
    private Integer status;




}
