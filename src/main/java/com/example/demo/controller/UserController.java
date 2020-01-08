package com.example.demo.controller;

import com.example.demo.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user")
@Api(value = "用户管理",description="用户管理")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @ApiOperation("登陆")
    public Map<String,Object> login(@RequestParam(value = "username",required = true)String username, @RequestParam(value = "password",required = true)String password){
        return userService.login(username,password);
    }

    @PostMapping("/selectAll")
    @ApiOperation("查询全部用户")
    public Map<String,Object> selectAll(){
        return userService.selectAll();
    }

    @PostMapping("/addUser")
    @ApiOperation("添加用户")
    public Map<String,Object> addUser(@RequestParam(value = "username",required = true)String username,@RequestParam(value = "password",required = true)String password){
        return userService.addUser(username,password);
    }

    @PostMapping("/getUserById")
    @ApiOperation("根据id查询用户信息")
    public Map<String,Object> getUserById(@RequestParam(value = "id",required = true)Integer id){
        return userService.getUserById(id);
    }

    @PostMapping("/updateUserById")
    @ApiOperation("根据id修改用户信息")
    public Map<String,Object> updateUserById(@RequestParam(value = "id",required = true)Integer id,@RequestParam(value = "username",required = true)String username,@RequestParam(value = "password",required = true)String password){
        return userService.updateUserById(username,password,id);
    }

    @PostMapping("/updatePowerById")
    @ApiOperation("根据id修改权限")
    public Map<String,Object> updatePowerById(@RequestParam(value = "power",required = true)Integer power,@RequestParam(value = "id",required = true)Integer id){
        return userService.updatePowerById(power,id);
    }

    @PostMapping("/deleteUserById")
    @ApiOperation("根据id删除用户信息")
    public Map<String,Object> deleteUserById(@RequestParam(value = "id",required = true)Integer id){
        return userService.deleteUserById(id);
    }

    @PostMapping("/getUsernameLike")
    @ApiOperation("根据用户名模糊查询")
    public Map<String,Object> getUsernameLike(@RequestParam(value = "name",required = true)String name){
        return userService.getUsernameLike(name);
    }

}
