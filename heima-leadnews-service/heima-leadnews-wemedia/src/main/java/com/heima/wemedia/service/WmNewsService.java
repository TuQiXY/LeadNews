package com.heima.wemedia.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmAuditDto;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.model.wemedia.pojos.WmNews;

public interface WmNewsService extends IService<WmNews> {

    /**
     * 查询文章
     * @param dto
     * @return
     */
    public ResponseResult findAll(WmNewsPageReqDto dto);


    /**
     *  发布文章或保存草稿
     * @param dto
     * @return
     */
    public ResponseResult submitNews(WmNewsDto dto);

    /**
     * 查询文章详情
     * @param id
     * @return
     */
    public ResponseResult findOne(Integer id);

    /**
     * 删除文章
     * @param id
     * @return
     */
    ResponseResult deleteNews(Integer id);


    /**
     * 文章的上下架
     * @param dto
     * @return
     */
    public ResponseResult downOrUp(WmNewsDto dto);

    /**
     * 自媒体文章分页条件查询
     * @param dto
     * @return
     */
    ResponseResult pageQuery(WmNewsPageReqDto dto);

    ResponseResult pass(WmAuditDto auditDto);

    ResponseResult failPass(WmAuditDto auditDto);
}