package com.etoak.mapper;

import com.etoak.bean.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int addUser(User user);

    User queryByName(@Param("name")String name);
}
