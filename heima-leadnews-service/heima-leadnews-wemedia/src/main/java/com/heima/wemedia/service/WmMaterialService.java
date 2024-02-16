package com.heima.wemedia.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.pojos.WmMaterial;
import com.heima.model.wemedia.pojos.WmMaterialDto;
import org.springframework.web.multipart.MultipartFile;

public interface WmMaterialService extends IService<WmMaterial> {

    /**
     * 图片上传
     * @param multipartFile
     * @return
     */
    ResponseResult uploadPicture(MultipartFile multipartFile);



    /**
     * 素材列表查询
     * @param dto
     * @return
     */
    public ResponseResult findList( WmMaterialDto dto);


    /**
     * 删除图片
     * @param id
     * @return
     */
    ResponseResult deletePicture(Integer id);

    /**
     * 取消收藏
     * @param id
     * @return
     */
    ResponseResult cancelConnection(Integer id);

    /**
     * 收藏
     * @param id
     * @return
     */
    ResponseResult connection(Integer id);


}