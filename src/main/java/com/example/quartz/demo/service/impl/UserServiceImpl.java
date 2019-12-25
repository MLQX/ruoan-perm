package com.example.quartz.demo.service.impl;

import com.example.quartz.demo.mapper.MenuMapper;
//import com.example.quartz.demo.mapper.UserMapper;
import com.example.quartz.demo.pojo.Menu;
import com.example.quartz.demo.service.UserService;
import com.example.quartz.demo.tool.consts.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
//    @Autowired
//    private UserMapper userMapper;
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Menu> queryMenuListByUserId(Long userId) {
        //通过获取到的id列表返回菜单字符串列表
        List<Long> menuIds = new ArrayList<>();
        if (userId == Constant.SUPER_ADMIN) {
            //超级管理员
            return getAllMenus(null);
        }
//        menuIds = userMapper.queryAllMenus(userId);
//        return  getAllMenus(menuIds);
        return null;
    }


    /**
     * 通过菜单的id获取所有的菜单   //CHECK
     * @param menuIds
     * @return
     */
    public List<Menu> getAllMenus(List<Long> menuIds){
        //查询根菜单
        List<Menu> menuList = queryListByParentId(0L,menuIds);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIds);

        return menuList;
    }

    private List<Menu> getMenuTreeList(List<Menu> menuList, List<Long> menuIds) {
        List<Menu> subMenuList = new ArrayList<>();
        for (Menu entity : menuList) {
            if (entity.getType() == Constant.MenuEnum.DIRECTORY.getValue()) {
                entity.setList(getMenuTreeList(queryListByParentId(entity.getMenuId(),menuIds),menuIds));
            }
            subMenuList.add(entity);
        }
        return subMenuList;

    }

    public  List<Menu> queryListByParentId(long parentId, List<Long> menuIdList) {
        List<Menu> menuList = queryListByParentId(parentId);
        if (menuIdList == null) return menuList;
        List<Menu> userMenuList = new ArrayList<>();
        for (Menu menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;


    }

    public List<Menu> queryListByParentId(Long parentId) {
        return menuMapper.queryListByParentId(parentId);
    }
}
