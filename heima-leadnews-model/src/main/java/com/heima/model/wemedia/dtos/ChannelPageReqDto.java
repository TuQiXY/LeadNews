package com.heima.model.wemedia.dtos;

import com.heima.model.common.dtos.PageRequestDto;
import lombok.Data;

/**
 * 频道分页条件查询dto
 */
@Data
public class ChannelPageReqDto extends PageRequestDto {

    /**
     *  频道名称
     */
    private String name;


}
