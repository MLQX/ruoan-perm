package com.example.quartz.demo.controller;

import com.example.quartz.demo.pojo.User;
import com.example.quartz.demo.tool.shiro.ShiroUtil;
import com.example.quartz.demo.tool.utils.R;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
public class LoginController {

    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private Producer producer;
    // 主页面
    @RequestMapping(value = {"/","/index.html"},method = RequestMethod.GET)
    public String index(){
        return "index";
    }

    //登录页
    @RequestMapping(value = {"/login.html"},method = RequestMethod.GET)
    public String logining(){
        return "login";
    }

    @RequestMapping(value = "main.html",method = RequestMethod.GET)
    public String main(){
        return "main";
    }

    @RequestMapping(value = "/captcha.jpg",method = RequestMethod.GET)
    public void captcha(HttpServletResponse response){
        //设置回复
        response.setHeader("Cache-Control","no-store,no-cache");
        response.setContentType("image/jpeg");

        //写验证码文字
        String text = producer.createText();
        //包裹图片
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtil.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY,text);
        try {
            //获取输出流
            ServletOutputStream stream = response.getOutputStream();
            //图片写到输出流
            ImageIO.write(image,"jpeg",stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @RequestMapping("404.html")
    public String notFound(){
        return "404";
    }

    //登录验证
    @ResponseBody
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public R login(String username,String password,String captcha){
        //对比验证码
        //1.获得session中的验证码
        String text = ShiroUtil.getSessionAttribute(Constants.KAPTCHA_SESSION_KEY).toString();
        logger.info("当前验证码内容为: "+text);
        //2.与前台上传的比较
        if (text != null) {
            if (!text.equalsIgnoreCase(captcha)) {
                return R.error("验证码不正确");
            }
        }
        //验证权限，进行登录操作
        try {
            Subject subject = ShiroUtil.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
        }catch (UnknownAccountException e){
            return R.error("未知账户错误");
        }catch (LockedAccountException e){
            return R.error("账户锁定，请联系管理员");
        }catch (AuthenticationException e){
            return R.error("账户验证失败");
        }

        return R.ok();
    }

    @RequestMapping("/user/info")
    @ResponseBody
    public R getCurrentUser(){
        return R.ok().put("user", ShiroUtil.getUser());
    }





}
