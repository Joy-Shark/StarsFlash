package com.huistar.huistarapps.Contraller.User;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.huistar.huistarapps.Enty.User;
import com.huistar.huistarapps.Mapper.UserMapper;
import com.huistar.huistarapps.UtilsEnty.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserContraller {
    @Autowired
    private UserMapper userMapper;
//    通用方法

    @PostMapping("/cookie")
    public String getCookieValue(@CookieValue(value = "UserValue", required = false) String userValue) {
        System.out.println(userValue);
        if (userValue != null) {
            return "UserValue Cookie Value: " + userValue;
        } else {
            return "UserValue Cookie not found.";
        }
    }

    @PostMapping("/login")
    public ReturnObject login(@RequestBody Map<String,Object> map, HttpServletResponse response){
        String name = map.get("name").toString();
        int age = Integer.parseInt(map.get("age").toString());

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

// 设置查询条件，假设你要检查字段 field1 和 field2 是否满足某些条件
        queryWrapper.eq("name", name).eq("age", age);

// 使用 userMapper 来查询记录数量
        int count = userMapper.selectCount(queryWrapper);
        if(count>0){
            Cookie cookie = new Cookie("UserValue", name + age);
            cookie.setPath("/"); // 设置Cookie的路径为根路径，使其在整个应用程序中可用
            cookie.setMaxAge(3600); // 设置Cookie的过期时间为一小时（3600秒）
            response.addCookie(cookie);
            return new ReturnObject("200","登录成功！",null);
        }
        return new ReturnObject("400","登录失败！",null);
    }
    @PostMapping("/get")
    public ReturnObject getuser(HttpServletRequest request){
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("UserValue")) {
//                    // 找到名为 "user" 的 Cookie
//                    String userCookieValue = cookie.getValue();
                    // 处理 Cookie 值
                    List<User> users = userMapper.selectList(null);
//                    for (User user :
//                            users) {
//                        if ((user.getName() + user.getAge()).equals(userCookieValue)) {
                            return new ReturnObject("200","拉取成功！",users);
//                        }
//                    }
//                    return new ReturnObject("400","似乎账户错误……",null);
//                }
//                return new ReturnObject("400","尚未登录……",null);
//            }
//            return new ReturnObject("400","尚未登录……",null);
//        }
//        return new ReturnObject("400","您似乎还未登录",null);
    }

    @PostMapping("/change")
    public ReturnObject changeUser(@RequestBody Map<String,Object> map,HttpServletRequest request){
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("UserValue")) {
//                    // 找到名为 "user" 的 Cookie
//                    String userCookieValue = cookie.getValue();
//                    // 处理 Cookie 值
//                    List<User> users = userMapper.selectList(null);
//                    for (User user :
//                            users) {
//                        if ((user.getName() + user.getAge()).equals(userCookieValue)) {

                            int id = Integer.parseInt(map.get("id").toString());
                            String name = map.get("name").toString();
                            int age = Integer.parseInt(map.get("age").toString());
                            User x = new User(id, name, age);
                            int update;
                            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
                            queryWrapper.eq("id",x.getId());
                            if(userMapper.selectList(queryWrapper).size()>0){
                                update = userMapper.updateById(x);
                            }else{

                                update = userMapper.insert(x);
                                return new ReturnObject("200","添加成功！！",null);
                            }
                            System.out.println(update);

                            if(update>0){
                                return new ReturnObject("200","更新成功！！",null);
                            }else{
                                return new ReturnObject("400","未进行任何更改！！",update);
                            }

//
//                        }
//                    }
//                    return new ReturnObject("400","似乎账户错误……",null);
//                }
//                return new ReturnObject("400","尚未登录……",null);
//            }
//            return new ReturnObject("400","尚未登录……",null);
//        }
//        return new ReturnObject("400","您似乎还未登录",null);


    }
    @PostMapping("/add")
    public ReturnObject addUser(@RequestBody Map<String,Object> map){
        String name = map.get("name").toString();
        int age = Integer.parseInt(map.get("age").toString());
//        User user = new User(null, name, age);
        User user = new User();
        user.setAge(age);
        user.setName(name);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

// 设置查询条件，假设你要检查字段 field1 和 field2 是否满足某些条件
        queryWrapper.eq("name", name);
        List<User> users = userMapper.selectList(queryWrapper);
        if (users.size()>0){
            return new ReturnObject("400","此用户已存在！！",user);
        }

        int insert = userMapper.insert(user);
        if(insert>0){
            return new ReturnObject("200","添加成功！！",null);
        }else{
            return new ReturnObject("400","更新失败！！",insert);
        }

    }
    @PostMapping("/del")
    public ReturnObject deluser(@RequestBody Map<String,Object> map) {
        int id = Integer.parseInt(map.get("id").toString());
        int i = userMapper.deleteById(id);
        if(i>0){
            return new ReturnObject("200","删除成功！",null);
        }else{
            return new ReturnObject("400","删除失败！",null);
        }

    }
}
