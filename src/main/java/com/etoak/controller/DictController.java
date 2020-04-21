package com.etoak.controller;

import com.etoak.bean.Dict;

import com.etoak.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(":/dict")
@Slf4j
@Api(tags = "字典项查询服务")
public class DictController {

    @Autowired
    DictService dictService;

    @GetMapping("/{groupId}")
    @ApiOperation(value = "根据groupid查字典项列表",notes = "根据groupid查字典项列表")
    @ApiImplicitParam(value = "字典项id",name = "groupId",required = true,paramType = "path")
    public List<Dict> queryList(@PathVariable("groupId")String groupId){
        log.info("groupId:{}",groupId);
        return dictService.queryList(groupId);
    }
}
