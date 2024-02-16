package com.heima.model.wemedia.dtos;

import lombok.Data;

/**
 * 文章审核dto
 */
@Data
public class WmAuditDto {

    /**
     *  文章id
     */
    private Integer id;
    /**
     *  文章状态
     */
    private Integer status;
    /**
     *  文章驳回理由 (前端是title,但是应该不是这个，而是region)
     */
    private String title;
    /**
     *  文章驳回理由 (前端是msg,但是应该不是这个，而是region)
     */
    private String msg;

    /**
     *  我只想说，这前端做的一坨屎，答辩
     */



}
