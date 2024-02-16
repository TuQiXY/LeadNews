package com.heima.common.constants;

import io.swagger.models.auth.In;

/**
 * 文章状态常量类
 */
public class WmNewsStatus {

    /**
     *  审核成功 9
     */
    public static final Integer SUCCESS = 9;
    /**
     *  等待人工审核
     */
    public static final Integer WAIT_HUMAN_AUDIT = 3;
    /**
     * 人工审核通过
     */
    public static final Integer SUCCESS_HUMAN_AUDIT = 4;

    /**
     * 等待 自动审核
     */
    public static final Integer WAIT_AUTO_AUDIT = 1;

    /**
     *  自动审核不通过
     */
    public static final Integer FAIL_AUDIT = 2;

}
