package com.heima.wemedia.fegin;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.heima.apis.wmedia.IWMediaClient;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.SensitivePageReqDto;
import com.heima.model.wemedia.pojos.WmUser;
import com.heima.wemedia.service.WmChannelService;
import com.heima.wemedia.service.WmSensitiveService;
import com.heima.wemedia.service.WmUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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


    @Autowired
    private WmUserService wmUserService;

    @Override
    @GetMapping("/api/v1/user/findByName/{name}")
    public WmUser findWmUserByName(@PathVariable("name") String name) {
        return wmUserService.getOne(Wrappers.<WmUser>lambdaQuery().eq(WmUser::getName, name));
    }

    @Override
    @PostMapping("/api/v1/wm_user/save")
    public ResponseResult saveWmUser(@RequestBody WmUser wmUser) {
        wmUserService.save(wmUser);
        return ResponseResult.okResult(AppHttpCodeEnum.SUCCESS);
    }

    @GetMapping("/api/v1/channel/list")
    @Override
    public ResponseResult getChannels() {
        return wmChannelService.findAll();
    }

}
