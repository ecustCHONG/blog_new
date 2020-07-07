package com.star.web;

import com.star.util.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TestController {

    @RequestMapping("/test")
    public String toTest(Model model){
        model.addAttribute("message","test success!");
        return "test";
    }
    @RequestMapping("/test/add")
    public String add(){
        return "testadd";
    }
    @RequestMapping("/test/update")
    public String update(){
        return "testupdate";
    }

    @GetMapping("/test/login")
    public String toLogin(){
        return "testlogin";
    }

    @PostMapping("/test/login")
    public String login(@RequestParam String username,@RequestParam String password,Model model) {
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, MD5Utils.code(password));
        try {
            subject.login(token);
            return "test";
        }catch (UnknownAccountException e){
            model.addAttribute("msg","用户名不正确");
            return "testlogin";
        }catch (IncorrectCredentialsException e){
            model.addAttribute("msg","密码错误");
            return "testlogin";
        }
    }

//    @RequestMapping("/unauthorized")
//    public String unauthorized(){
//        return "admin/unauthorized";
//    }
}
