package com.example.demo.service.Impl;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 登陆
     * @param username
     * @param password
     * @return
     */
    public Map<String,Object> login(String username,String password){
        Map<String, Object> map = new HashMap<>();
        List<User> userList = userDao.loginAll();
        for (User user:userList) {
            user.setPassword(decode(user.getPassword().getBytes()));
        }
        for (User user:userList) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                map.put("msg","登陆成功！");
                map.put("code",200);
                map.put("username",username);
                map.put("id",user.getId());
                map.put("power",user.getPower());
                return map;
            }
        }
        map.put("msg","用户名或密码错误！");
        map.put("code",201);
        return map;
    }

    /**
     * 查询全部用户
     * @return
     */
    public Map<String,Object> selectAll(){
        Map<String, Object> map = new HashMap<>();
        List<User> userList = userDao.selectAll();
        for (User user:userList) {
            user.setPassword(decode(user.getPassword().getBytes()));
        }
        if(userList.size()>0){
            map.put("msg","查询成功");
            map.put("code",200);
            map.put("data",userList);
            return map;
        }else {
            map.put("msg","查询失败");
            map.put("code",201);
            return map;
        }
    }

    /**
     * 添加用户
     * @param username
     * @param password
     * @return
     */
    public Map<String,Object> addUser(String username,String password){
        Map<String, Object> map = new HashMap<>();
        password = encode(password.getBytes());
        List<User> userList = userDao.selectAll();
        for (User user:userList) {
            if(user.getUsername().equals(username)){
                map.put("msg","添加失败");
                map.put("code",201);
                map.put("data","用户名已存在");
                return map;
            }
        }
        int a = userDao.addUser(username,password);
        if(a==1){
            map.put("msg","添加成功");
            map.put("code",200);
            map.put("username",username);
            map.put("psaaword",password);
            return map;
        }else {
            map.put("msg","添加失败");
            map.put("code",201);
            return map;
        }
    }

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    public Map<String,Object> getUserById(Integer id){
        Map<String, Object> map = new HashMap<>();
        User user = userDao.getUserById(id);
        user.setPassword(decode(user.getPassword().getBytes()));
        map.put("msg","查询成功");
        map.put("code",200);
        map.put("id",user.getId());
        map.put("username",user.getUsername());
        map.put("password",user.getPassword());
        map.put("power",user.getPower());
        return map;
    }

    /**
     * 根据id修改用户信息
     * @param username
     * @param password
     * @param id
     * @return
     */
    public Map<String,Object> updateUserById(String username,String password,Integer id){
        Map<String, Object> map = new HashMap<>();
        password = encode(password.getBytes());
        int a = userDao.updateUserById(username,password,id);
        if(a==1){
            map.put("msg","修改成功");
            map.put("code",200);
            map.put("id",id);
            map.put("username",username);
            map.put("password",password);
            return map;
        }else {
            map.put("msg","修改失败");
            map.put("code",201);
            map.put("data","没有此用户");
            return map;
        }
    }

    /**
     * 根据id修改权限
     * @param power
     * @param id
     * @return
     */
    public Map<String,Object> updatePowerById(Integer power,Integer id){
        Map<String, Object> map = new HashMap<>();
        int a = userDao.updatePowerById(power,id);
        if (a==1){
            map.put("msg","修改成功");
            map.put("code",200);
            map.put("id",id);
            map.put("power",power);
            return map;
        }else{
            map.put("msg","修改失败");
            map.put("code",201);
            map.put("data","没有此用户");
            return map;
        }
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    public Map<String,Object> deleteUserById(Integer id){
        Map<String, Object> map = new HashMap<>();
        int a = userDao.deleteUserById(id);
        if (a==1){
            map.put("msg","删除成功");
            map.put("code",200);
            map.put("id",id);
            return map;
        }else {
            map.put("msg","删除失败");
            map.put("code",201);
            map.put("data","没有此用户");
            return map;
        }
    }

    /**
     * 根据用户名模糊查询
     * @param name
     * @return
     */
    public Map<String,Object> getUsernameLike(String name){
        Map<String, Object> map = new HashMap<>();
        List<User> userList = userDao.getUsernameLike(name);
        List<User> userListAll = userDao.selectAll();
        for (User user:userListAll) {
            user.setPassword(decode(user.getPassword().getBytes()));
        }
        for (User user:userList) {
            user.setPassword(decode(user.getPassword().getBytes()));
        }
        if(name.equals("")){
            if(userListAll.size()>0){
                map.put("msg","查询成功");
                map.put("code",200);
                map.put("data",userListAll);
                return map;
            }else {
                map.put("msg","查询失败");
                map.put("code",201);
                map.put("data","没有此用户");
                return map;
            }
        }else {
            if(userList.size()>0){
                map.put("msg","查询成功");
                map.put("code",200);
                map.put("data",userList);
                return map;
            }else {
                map.put("msg","查询失败");
                map.put("code",201);
                map.put("data","没有此用户");
                return map;
            }
        }
    }


    /**
     * Base64解密
     * @param bytes
     * @return
     */
    private String decode(byte[] bytes) {
        return new String(Base64.decodeBase64(bytes));
    }

    /**
     * Base64加密
     * @param bytes
     * @return
     */
    private String encode(byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }


}
