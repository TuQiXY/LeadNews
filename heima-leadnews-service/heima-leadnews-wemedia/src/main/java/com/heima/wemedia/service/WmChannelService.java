package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.ChannelPageReqDto;
import com.heima.model.wemedia.pojos.WmChannel;

public interface WmChannelService extends IService<WmChannel> {

    /**
     * 查询所有频道
     * @return
     */
    public ResponseResult findAll();

    /**
     * 分页条件查询
     * @param channelPageReqDto
     * @return
     */
    ResponseResult pageQuery(ChannelPageReqDto channelPageReqDto);

    ResponseResult deleteChannel(Integer id);

    ResponseResult addOne(WmChannel wmChannel);

    ResponseResult updateChannel(WmChannel wmChannel);
}
