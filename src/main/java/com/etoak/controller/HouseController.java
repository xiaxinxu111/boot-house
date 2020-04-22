package com.etoak.controller;

import com.etoak.bean.House;
import com.etoak.bean.HouseVo;
import com.etoak.bean.Page;
import com.etoak.exception.ParamException;
import com.etoak.service.HouseService;
import com.etoak.utils.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/house")
@Validated
@Slf4j
public class HouseController {

    @Value("${upload.dir}")
    private String uploadDirectory;

    @Value("${upload.savePathPrefix}")
    private String savePathPrefix;

    @Autowired
    HouseService houseService;

    @RequestMapping("/toAdd")
    public String toAdd(){
        return "house/add";
    }

    @PostMapping("/add")
    public String add(@RequestParam("file")MultipartFile file, House house, BindingResult bindingResult) throws IOException,IllegalStateException {
        ValidationUtil.validate(house);
        //上传文件
        //获取文件的原始名称
        String originalFileName = file.getOriginalFilename();
        String prefix = UUID.randomUUID().toString().replaceAll("-","");
        //组合新的文件名称
        String newFileName =prefix + "_" + originalFileName;
        //上传文件目录
        File destFile = new File(this.uploadDirectory,newFileName);
        file.transferTo(destFile);
        //给House设置访问地址
        house.setPic(this.savePathPrefix+newFileName);
        houseService.addHouse(house);
        return "redirect:/house/toAdd";
    }

    @PostMapping("/add2")
    public String add2(@RequestParam("file")MultipartFile file, House house, BindingResult bindingResult) throws IOException,IllegalStateException {
        //对参数进行校验
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if(CollectionUtils.isNotEmpty(allErrors)){
            StringBuffer msgBuffer = new StringBuffer();
            for(ObjectError objectError : allErrors){
                String message = objectError.getDefaultMessage();
                msgBuffer.append(message).append(";");
            }
            throw new ParamException("参数校验失败：" + msgBuffer.toString());
        }

        //上传文件
        //获取文件的原始名称
        String originalFileName = file.getOriginalFilename();
        String prefix = UUID.randomUUID().toString().replaceAll("-","");
        //组合新的文件名称
        String newFileName =prefix + "_" + originalFileName;
        //上传文件目录
        File destFile = new File(this.uploadDirectory,newFileName);
        file.transferTo(destFile);
        //给House设置访问地址
        house.setPic(this.savePathPrefix+newFileName);

        houseService.addHouse(house);

        return "redirect:/house/toAdd";
    }

    @GetMapping(value = "/list", produces = "application/json;charset=utf-8")
    @ResponseBody
    public Page<HouseVo> queryList(@RequestParam(required = false,defaultValue = "1")int pageNum,@RequestParam(required = false,defaultValue = "10")int pageSize,HouseVo houseVo){
        log.info("pageNum:{},pageSize:{},HouseVo:{}",pageNum,pageSize,houseVo);
        return houseService.queryList(pageNum,pageSize,houseVo);
    }
}
