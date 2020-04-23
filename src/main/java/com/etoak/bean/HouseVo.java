package com.etoak.bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class HouseVo extends  House{

    // 租赁方式名称
    private String rentModeName;
    // 户型名称
    private String houseTypeName;
    // 朝向名称
    private String orientationName;
    // 接收前端的户型参数
    @JsonIgnore
    private String[] houseTypeList;

    @JsonIgnore
    private List<String> orientationList;

    @JsonIgnore
    private List<Map<String,Integer>> rentalMapList;

}
