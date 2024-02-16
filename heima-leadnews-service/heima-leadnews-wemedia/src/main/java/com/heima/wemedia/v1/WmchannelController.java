package com.heima.wemedia.v1;



import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.ChannelPageReqDto;
import com.heima.model.wemedia.pojos.WmChannel;
import com.heima.wemedia.service.WmChannelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Api("频道管理")
@RestController
@RequestMapping("/api/v1/channel")
@Slf4j
public class WmchannelController {
    @Autowired
    private WmChannelService wmChannelService;


    @GetMapping("/channels")
    public ResponseResult findAll(){
        return wmChannelService.findAll();
    }
    @ApiOperation("分页条件查询频道")
    @PostMapping("/list")
    public ResponseResult pageQuery(@RequestBody ChannelPageReqDto channelPageReqDto){
        return wmChannelService.pageQuery(channelPageReqDto);
    }

    @ApiOperation("删除频道")
    @GetMapping("/del/{id}")
    @Transactional
    public ResponseResult delChannel(@PathVariable("id") Integer id){
        return wmChannelService.deleteChannel(id);
    }

    @ApiOperation("新增频道")
    @PostMapping("/save")
    @Transactional
    public ResponseResult saveChannel(@RequestBody WmChannel wmChannel){
         return wmChannelService.addOne(wmChannel);
    }

    @ApiOperation("修改频道")
    @PostMapping("/update")
    @Transactional
    public ResponseResult updateChannel(@RequestBody WmChannel wmChannel){
        return wmChannelService.updateChannel(wmChannel);
    }
}
