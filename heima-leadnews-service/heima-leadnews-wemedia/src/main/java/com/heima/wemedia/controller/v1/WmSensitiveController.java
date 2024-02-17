package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.wemedia.dtos.SensitivePageReqDto;
import com.heima.model.wemedia.pojos.WmSensitive;
import com.heima.wemedia.service.WmSensitiveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/sensitive")
@Api("敏感词管理")
@Slf4j
public class WmSensitiveController {

    @Resource
    private WmSensitiveService wmSensitiveService;


    @ApiOperation("分页条件查询敏感词")
    @PostMapping("/list")
    public ResponseResult page(@RequestBody SensitivePageReqDto sensitivePageReqDto) {
        return wmSensitiveService.pageList(sensitivePageReqDto);
    }

    @ApiOperation("根据id删除敏感词")
    @DeleteMapping("/del/{id}")
    @Transactional
    public ResponseResult delete(@PathVariable("id") Integer id) {
        boolean b = wmSensitiveService.removeById(id);
        if (b) {
            return ResponseResult.okResult("删除成功!");
        } else {
            log.error("根据id删除敏感词失败：{}", id);
            return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "删除失败！");
        }
    }

    @ApiOperation("新增敏感词")
    @PostMapping("/save")
    @Transactional
    public ResponseResult save(@RequestBody WmSensitive sensitive) {

        sensitive.setCreatedTime(new Date());

        boolean b = wmSensitiveService.save(sensitive);
        if (b) {
            return ResponseResult.okResult("新增成功!");
        } else {
            log.error("新增敏感词失败：{}", sensitive);
            return
                    ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "新增失败！");
        }


    }

    @ApiOperation("修改敏感词")
    @PostMapping("/update")
    @Transactional
    public ResponseResult update(@RequestBody WmSensitive sensitive) {
        boolean b = wmSensitiveService.updateById(sensitive);
        if (b) {
            return ResponseResult.okResult("修改成功!");
        } else {
            log.error("修改敏感词失败：{}", sensitive);
            return
                    ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR, "修改失败！");
        }
    }

}
