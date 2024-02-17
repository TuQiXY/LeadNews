package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmMaterialDto;
import com.heima.wemedia.service.WmMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/material")
public class WmMaterialController {

    @Autowired
    private WmMaterialService wmMaterialService;

    /**
     * 上传图片
     * @param multipartFile
     * @return
     */
    @PostMapping("/upload_picture")
    public ResponseResult uploadPicture(MultipartFile multipartFile){
        return wmMaterialService.uploadPicture(multipartFile);
    }

    /**
     * 素材列表查询
     * @param dto
     * @return
     */
    @PostMapping("/list")
    public ResponseResult findList(@RequestBody WmMaterialDto dto){
        return wmMaterialService.findList(dto);
    }

    @GetMapping("/del_picture/{id}")
    public ResponseResult deletePicture(@PathVariable Integer id){
        return wmMaterialService.deletePicture(id);
    }

    @GetMapping("/cancel_collect/{id}")
    public ResponseResult cancelConnection(@PathVariable Integer id){
        return wmMaterialService.cancelConnection(id);
    }

    @GetMapping("/collect/{id}")
    public ResponseResult connection(@PathVariable Integer id){
        return wmMaterialService.connection(id);
    }

}
