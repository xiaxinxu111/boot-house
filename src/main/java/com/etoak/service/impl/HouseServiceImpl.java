package com.etoak.service.impl;

import com.etoak.bean.Area;
import com.etoak.bean.House;
import com.etoak.bean.HouseVo;
import com.etoak.bean.Page;
import com.etoak.mapper.AreaMapper;
import com.etoak.mapper.HouseMapper;
import com.etoak.service.HouseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HouseServiceImpl implements HouseService {

    @Autowired
    HouseMapper houseMapper;

    @Autowired
    AreaMapper areaMapper;

    @Override
    public int addHouse(House house) {
        Area area = areaMapper.queryById(house.getArea());
        if(area == null){
            log.error("未查询到所在地址,查询的所在区id:{}",house.getArea());
            throw new RuntimeException("服务端 异常");
        }
        house.setAreaName(area.getName());
        return houseMapper.addHouse(house);
    }

    @Override
    public Page<HouseVo> queryList(int pageNum,int pageSize,HouseVo houseVo) {
        PageHelper.startPage(pageNum, pageSize);
        List<HouseVo> rows =  houseMapper.queryList(houseVo);
        PageInfo<HouseVo> pageInfo = new PageInfo<>(rows);
        return new Page<HouseVo>(pageInfo.getPageNum(),pageInfo.getPageSize(),rows,pageInfo.getTotal(),pageInfo.getPages());




    }


}
