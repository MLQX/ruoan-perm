package com.example.quartz.demo.controller;

import com.example.quartz.demo.pojo.Menu;
import com.example.quartz.demo.pojo.User;
import com.example.quartz.demo.service.UserService;
import com.example.quartz.demo.tool.shiro.ShiroUtil;
import com.example.quartz.demo.tool.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private UserService userService;
    @RequestMapping("/nav")
    @ResponseBody
    public R getNavs(){
        // 通过用户id来获取角色，通过角色来获取menu权限
        User user = ShiroUtil.getUser();
        List<Menu> menuList = userService.queryMenuListByUserId(user.getUserId());
        return R.ok().put("menuList", menuList);
    }
}
