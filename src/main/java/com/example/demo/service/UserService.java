package com.example.demo.service;

import java.util.Map;

public interface UserService {

    Map<String, Object> login(String username, String password);

    Map<String, Object> selectAll();

    Map<String, Object> addUser(String username, String password);

    Map<String, Object> getUserById(Integer id);

    Map<String,Object> updateUserById(String username,String password,Integer id);

    Map<String,Object> updatePowerById(Integer power,Integer id);

    Map<String,Object> deleteUserById(Integer id);

    Map<String,Object> getUsernameLike(String name);
}
