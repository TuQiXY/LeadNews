package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmMaterialDto;
import com.heima.wemedia.service.WmMaterialService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/material")
@Api(tags = "自媒体端素材管理")
public class WmMaterialController {

    @Autowired
    private WmMaterialService wmMaterialService;

    @ApiOperation("上传图片素材")
    @PostMapping("/upload_picture")
    public ResponseResult uploadPicture(MultipartFile multipartFile){
        return wmMaterialService.uploadPicture(multipartFile);
    }

    @ApiOperation("图片素材列表查询")
    @PostMapping("/list")
    public ResponseResult findList(@RequestBody WmMaterialDto dto){
        return wmMaterialService.findList(dto);
    }

    @ApiOperation("删除图片素材")
    @GetMapping("/del_picture/{id}")
    public ResponseResult deletePicture(@PathVariable Integer id){
        return wmMaterialService.deletePicture(id);
    }

    @ApiOperation("取消收藏")
    @GetMapping("/cancel_collect/{id}")
    public ResponseResult cancelConnection(@PathVariable Integer id){
        return wmMaterialService.cancelConnection(id);
    }

    @ApiOperation("收藏素材")
    @GetMapping("/collect/{id}")
    public ResponseResult connection(@PathVariable Integer id){
        return wmMaterialService.connection(id);
    }

}
