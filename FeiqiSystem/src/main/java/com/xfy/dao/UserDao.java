package com.xfy.dao;

import com.xfy.entity.Params;
import com.xfy.entity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Repository
public interface UserDao extends Mapper<User> {
    /*查询用户*/
    List<User> findUser(@Param("params") Params params);

    /*判断用户唯一*/
    @Select("select * from users where username = #{username} limit 1")
    User findByUserName(@Param("username") String username);

    @Select("select * from users where username = #{username} and password = #{password} limit 1")
    User findByUserNameAndPassword(@Param("username") String username, @Param("password") String password);
}


