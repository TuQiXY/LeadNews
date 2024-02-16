package com.heima.wemedia.fegin;

import com.heima.apis.wmedia.IWMediaClient;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.SensitivePageReqDto;
import com.heima.wemedia.service.WmChannelService;
import com.heima.wemedia.service.WmSensitiveService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class WmClient implements IWMediaClient {

    @Resource
    private WmSensitiveService wmSensitiveService;

    @Resource
    private WmChannelService wmChannelService;
    @Override
    @PostMapping("/v1/sensitive/list")
    public ResponseResult pageSensitive(SensitivePageReqDto sensitivePageReqDto) {
        return wmSensitiveService.pageList(sensitivePageReqDto);
    }




    @GetMapping("/api/v1/channel/list")
    @Override
    public ResponseResult getChannels() {
        return wmChannelService.findAll();
    }
}
