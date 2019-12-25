package com.example.quartz.demo.tool.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.quartz.demo.mapper.MenuMapper;
//import com.example.quartz.demo.mapper.UserMapper;
import com.example.quartz.demo.pojo.Menu;
import com.example.quartz.demo.pojo.User;
import com.example.quartz.demo.tool.consts.Constant;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;

import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 用户认证模块
 */
@Component
public class UserRealm extends AuthorizingRealm {

//    @Autowired
//    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;



    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) throws AuthorizationException{
        // 权限操作  获取用户的所有权限
        User user = (User) principalCollection.getPrimaryPrincipal();
        Long userId = user.getUserId();
        List<String> permLists;
        if(userId == Constant.SUPER_ADMIN) {
            //超级管理员，拥有所有权限
            List<Menu> menuLists = menuMapper.selectList(null); //菜单列表
            permLists = new ArrayList<>(menuLists.size());
            for (Menu menu:menuLists) {
                permLists.add(menu.getPerms());  //获取每一个菜单对应的权限字符长串， 并加入到用户的权限列表中去
            }

        }else{
            //普通用户，查找权限
            //permLists = userMapper.queryAllPermissions(userId); //通过用户编号查找所有权限？？？
            permLists=null;

        }

        //去重，拼权限字符串
        HashSet<String> permSet = new HashSet<>();
        if(CollectionUtils.isEmpty(permLists)){
            throw new AuthorizationException("用户没有任何权限");
        }
        for (String perms : permLists) {
            if (StringUtils.isBlank(perms)) {
                //权限字符长串为空
                continue;
            }
            permSet.addAll(Arrays.asList(perms.trim().split(",")));  //去重操作

        }

        //回显权限信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setStringPermissions(permSet);
        return simpleAuthorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //获取token
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken)authenticationToken;
        //从数据库中查找该用户
        //User user =  userMapper.selectOne(new QueryWrapper<User>().eq("username",usernamePasswordToken.getUsername()));
        User  user = null;
        if (user == null) {
            throw new UnknownAccountException("未知用户");
        }else if(user.getStatus()==0){
            throw new LockedAccountException("用户账户已锁定");
        }

        //返回认证信息
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user,user.getPassword(), ByteSource.Util.bytes(user.getSalt()),this.getName());
        return simpleAuthenticationInfo;
    }


    //重要方法
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName(ShiroUtil.hashAlgorithmName);
        hashedCredentialsMatcher.setHashIterations(ShiroUtil.hashIterations);
        super.setCredentialsMatcher(hashedCredentialsMatcher);
    }
}
