package com.example.quartz.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class TestController {

    @Value("${renren.redis.open}")
    private boolean open = true;


    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello(HttpServletRequest req,HttpServletResponse rsp) {
        System.out.println(req);
        System.out.println(rsp);
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        String value = (String)servletRequestAttributes.getAttribute("user", RequestAttributes.SCOPE_SESSION);


        System.out.println("*****"+open);
        return "hello quartz2";

    }
}
