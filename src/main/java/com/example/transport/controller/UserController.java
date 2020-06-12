package com.example.transport.controller;

import com.example.transport.bean.User;
import com.example.transport.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @ResponseBody
    @RequestMapping("/register")
    public String register(HttpServletRequest request, Model model){
        String name =request.getParameter("name");
        String password =request.getParameter("password");
        String userId =request.getParameter("userId");
        String email =request.getParameter("email");
        String msg = "注册成功";
        System.out.println(userService.getUserId(userId));
        System.out.println(userService.getName(name));
        if(name == null || password == null
        || userId == null || email == null){
            msg = "信息不完整";
            return msg;
//      }else if(userService.getUserId2(userId).size()==0){
//             msg = "无此人员信息";
//            return msg;
        }else if(userService.getName(name).size()>0){
             msg = "此用户已注册，请登录";
            return msg;
        }else if(userService.getUserId(userId).size()>0){
             msg = "此用户已有账号，请登录";
            return msg;
        }
        User user = new User(name,password,userId,2,email);
        userService.add(user);
        return msg;
    }

    @ResponseBody
    @RequestMapping("/login")
    public String login(HttpServletRequest request, Model model){
        String name =request.getParameter("name");
        String password =request.getParameter("password");
        String msg = "登录成功";
//        System.out.println(userService.getName(name));
        if(name == null || name == ""){
            msg = "用户名不能为空";
            return msg;
        }
        if(password == null || password == ""){
            msg = "密码不能为空";
            return msg;
        }
        if(userService.getUser(name,password).size()==0){
            msg = "用户名或密码错误";
            return msg;
        }
        if(userService.getUser(name,password).get(0).getAdmin()==1){
            msg = "管理员登录成功";
        }
        User user = userService.getUser(name,password).get(0);
        request.getSession().setAttribute("user",user);
        return msg;
    }

    @ResponseBody
    @RequestMapping("/alluser")
    public List<User> allUser(){
        List<User> list = userService.getAll();
        return list;
    }

    @RequestMapping("/toregister")
    public String toRegister(){
        return "register";
    }

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }


}
