package com.etoak.service;

import com.etoak.bean.House;
import com.etoak.bean.HouseVo;
import com.etoak.bean.Page;

import java.util.List;

public interface HouseService {

    int addHouse(House house);

    Page<HouseVo> queryList(int pageNum,int pageSize,HouseVo houseVo,String[] rentalList);

    int updateHouse(House house);

    int deleteById(int id);
}
