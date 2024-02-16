package com.heima.model.wemedia.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

/**
 * 敏感词的分页查询dto
 */
@Data
public class SensitivePageReqDto extends PageRequestDto {
    /**
     * 敏感词名称
     */
   private String name;

}
