package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.ChannelDto;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.wemedia.service.WmChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/channel")
@Api(tags = "自媒体端文章频道管理")
public class WmchannelController {


    @Autowired
    private WmChannelService wmChannelService;


    @ApiOperation("查询所有频道")
    @GetMapping("/channels")
    public ResponseResult findAll(){
        return wmChannelService.findAll();
    }

    @ApiOperation("条件分页查询")
    @PostMapping("/list")
    public ResponseResult findByNameAndPage(@RequestBody ChannelDto dto){
        return wmChannelService.findByNameAndPage(dto);
    }

    /**
     * 保存频道
     * @param adChannel
     * @return
     */
    @PostMapping("/save")
    public ResponseResult insert(@RequestBody WmChannel adChannel){
        return wmChannelService.insert(adChannel);
    }

    /**
     * 修改
     * @param adChannel
     * @return
     */
    @PostMapping("/update")
    public ResponseResult update(@RequestBody WmChannel adChannel){
        return wmChannelService.update(adChannel);
    }

    /**
     * 删除频道
     * @param id
     * @return
     */
    @GetMapping("/del/{id}")
    public ResponseResult delete(@PathVariable("id") Integer id){
        return wmChannelService.delete(id);
    }


}
