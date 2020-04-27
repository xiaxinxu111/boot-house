package com.etoak.controller;

import com.etoak.bean.User;
import com.etoak.commons.CommonsResult;
import com.etoak.exception.ParamException;
import com.etoak.exception.UserLoginException;
import com.etoak.service.UserService;
import com.sun.xml.internal.fastinfoset.CommonResourceBundle;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RequestMapping("/user")
@Controller
@Slf4j
public class UserController {
    //用户的激活状态
    public static final int ACTIVE_STATE = 1;

    @Autowired
    UserService userService;

    @GetMapping("/toReg")
    public String toRegPage(){
        return "user/reg";
    }

    @PostMapping("/reg")
    public String reg(@RequestParam String confirmPassword, User user){
        if(!StringUtils.equals(confirmPassword,user.getPassword())){
            throw new ParamException("两次密码不一致");
        }
        userService.addUser(user);
        return "redirect:/user/toReg";
    }

    @GetMapping("/validateName")
    @ResponseBody
    public String validateName(@RequestParam String name){
        log.info("param name :{}",name);
        User user = userService.queryByName(name);
        return user == null ? "true" : "false";
    }

    @GetMapping("/toLogin")
    public String toLogin(){
        return "user/login";
    }

    @PostMapping("/login")
    @ResponseBody
    public CommonsResult login(
            @RequestParam String name,
            @RequestParam String password,
            @RequestParam String code,
            HttpServletRequest request){
        HttpSession session = request.getSession();
        String sessionCode = String.valueOf(session.getAttribute("code"));
        //判断session中的验证码和用户填写的验证码是否一致
        if(!StringUtils.equals(code,sessionCode)){
            throw new UserLoginException("验证码错误");
        }
        //根据用户名查询是否存在该用户
        User user = userService.queryByName(name);
        if(ObjectUtils.isEmpty(user)){
            log.warn("用户名错误");
            throw new UserLoginException("用户名或密码错误");
        }
        //判断用户状态是否为已激活
        if(user.getState() != ACTIVE_STATE){
            throw new UserLoginException("用户状态异常");
        }
        //判断用户密码是否正确
        password = DigestUtils.md5Hex(password);
        if(!StringUtils.equals(password,user.getPassword())){
            log.error("密码错误");
            throw new UserLoginException("用户名或密码错误");
        }
        //清空session 并重新创建一个Session
        session.invalidate();
        session = request.getSession();
        user.setPassword(null);
        session.setAttribute("user",user);
        return new CommonsResult(CommonsResult.SUCCESS_CODE,CommonsResult.SUCCESS_MSG);
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();
        return "redirect:/user/toLogin";
    }
}
