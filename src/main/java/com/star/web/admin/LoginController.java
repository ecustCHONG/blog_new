package com.star.web.admin;

import com.star.po.User;
import com.star.service.UserService;
import com.star.util.MD5Utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @Author: ONESTAR
 * @QQ群: 530311074
 * @URL: https://onestar.newstar.net.cn/
 */
@Controller
@RequestMapping("/admin")
public class LoginController {


    @Autowired
    private UserService userService;

    @GetMapping("")
    public String loginPage(){
        return "admin/login";
    }


//    @PostMapping("/login")
//    public String login(@RequestParam String username,
//                        @RequestParam String password,
//                        HttpSession session,
//                        RedirectAttributes attributes) {
//        User user = userService.checkUser(username, password);
//        if (user != null) {
//            user.setPassword(null);
//            session.setAttribute("user",user);
//            return "admin/index";
//        } else {
//            attributes.addFlashAttribute("message", "用户名和密码错误");
//            return "redirect:/admin";
//        }
//    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,  RedirectAttributes attributes,HttpSession session) {
        //获取当前用户
        Subject subject = SecurityUtils.getSubject();
        //封装用户的登录数据
        UsernamePasswordToken token = new UsernamePasswordToken(username, MD5Utils.code(password));
        try {
            subject.login(token);
            User user = userService.checkByUsername(token.getUsername());
            session.setAttribute("user",user);
            return "admin/index";
        }catch (UnknownAccountException e){
            attributes.addAttribute("message","用户名不正确");
            return "redirect:/admin";
        }catch (IncorrectCredentialsException e){
            attributes.addAttribute("message","密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/register1")
    public String register1(){
        return "admin/register";
    }
    @PostMapping("/register")
    public String register(@RequestParam String username,
                   @RequestParam String password,
                   HttpSession session,
                   RedirectAttributes attributes) {

        User user=userService.checkByUsername(username);
            if (user != null) {
                attributes.addFlashAttribute("message", "用户名已被使用，请重试！");
                return "admin/register";
            }else{
                userService.register(username,password);
                attributes.addFlashAttribute("message", "注册成功！");
                return "redirect:/admin";
            }

    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(){
        return "admin/unauthorized";
    }
}
