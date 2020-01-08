package com.example.demo.dao;

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDao {

    List<User> loginAll();

    List<User> selectAll();

    int addUser(@Param("username")String username,@Param("password") String password);

    User getUserById(Integer id);

    int updateUserById(@Param("username")String username,@Param("password") String password,@Param("id") Integer id);

    int updatePowerById(@Param("power")Integer power, @Param("id")Integer id);

    int deleteUserById(Integer id);

    List<User> getUsernameLike(String name);

}
