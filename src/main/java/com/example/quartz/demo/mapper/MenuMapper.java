package com.example.quartz.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.quartz.demo.pojo.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public interface MenuMapper extends BaseMapper<Menu> {


    //查询用户的所有权限
    public List<String> queryAllPermissions(Long userId);

    //查询用户的所有菜单
    public List<Menu> queryListByParentId(Long userId);


}
