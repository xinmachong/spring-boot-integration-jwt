package com.xinmachong.jwtdemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinmachong.jwtdemo.bean.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper extends BaseMapper<User> {
}
