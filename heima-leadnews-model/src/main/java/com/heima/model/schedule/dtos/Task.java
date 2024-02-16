package com.heima.model.schedule.dtos;

import lombok.Data;

import java.io.Serializable;

/**
 * 前端传过来的任务信息
 */
@Data
public class Task implements Serializable {

    /**
     * 任务id
     */
    private Long taskId;
    /**
     * 类型
     */
    private Integer taskType;

    /**
     * 优先级
     */
    private Integer priority;

    /**
     * 执行id   其实就是执行时间
     */
    private long executeTime;

    /**
     * task参数
     */
    private byte[] parameters;

}
