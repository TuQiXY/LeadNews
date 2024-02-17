package com.heima.apis.wmedia;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.SensitivePageReqDto;
import com.heima.model.wemedia.pojos.WmUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *  自媒体远程调用
 */
@FeignClient("leadnews-wemdia")
public interface IWMediaClient {

    /**
     * 敏感词的分页查询
     * @param sensitivePageReqDto
     * @return
     */
    @PostMapping("/api/v1/sensitive/list")
    ResponseResult pageSensitive(@RequestBody SensitivePageReqDto  sensitivePageReqDto);


    /**
     * 获取所有的频道
     * @return
     */
    @GetMapping("/api/v1/channel/list")
    public ResponseResult getChannels();

    @GetMapping("/api/v1/user/findByName/{name}")
    WmUser findWmUserByName(@PathVariable("name") String name);

    @PostMapping("/api/v1/wm_user/save")
    ResponseResult saveWmUser(@RequestBody WmUser wmUser);

}
