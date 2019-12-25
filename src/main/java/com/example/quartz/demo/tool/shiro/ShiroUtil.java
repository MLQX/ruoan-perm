package com.example.quartz.demo.tool.shiro;

import com.example.quartz.demo.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * shiro工具类
 */
public class ShiroUtil {

    // 加密方式
    public static final String hashAlgorithmName = "SHA-256";
    // 加密次数
    public static final int hashIterations = 16;

    //密码+盐  SHA-256方式的简单哈希
    public static String simpleHash(String password,String salt){
        return new SimpleHash(hashAlgorithmName, password, salt).toString();
    }


    //获取subject
    public static Subject getSubject(){
        return SecurityUtils.getSubject();
    }

    //获取session
    public static Session getSession(){
        return SecurityUtils.getSubject().getSession();
    }


    //获取用户
    public static User getUser(){
        return (User)SecurityUtils.getSubject().getPrincipal();
    }
    //获取用户id
    public static Long getUserId() {
        return getUser().getUserId();
    }

    //设置session属性
    public static void setSessionAttribute(Object key,Object value){
        getSession().setAttribute(key,value);
    }
    //读取session属性
    public static Object getSessionAttribute(Object key){
        return getSession().getAttribute(key);
    }

    //判断用户是否在线
    public static boolean isLogin(){
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    //用户离线
    public static void logout(){
        SecurityUtils.getSubject().logout();
    }

}

