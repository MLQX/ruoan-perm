package com.example.quartz.demo.service;

import com.example.quartz.demo.pojo.Menu;

import java.util.List;

public interface UserService {
    List<Menu> queryMenuListByUserId(Long userId);
}
