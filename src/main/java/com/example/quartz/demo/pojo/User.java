package com.example.quartz.demo.pojo;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@TableName("sys_user")
public class User implements Serializable {
    private static final long serialVersionUID = 4512459131641399136L;
    @TableId
    private Long userId; //用户ID

    @NotBlank(message="用户名不能为空")
    private String username;

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @NotBlank(message="密码不能为空")
    private String password;

    @NotBlank(message="邮箱不能为空")
    @Email(message="邮箱格式不正确")
    private String email;

    private Integer status;  //账户状态
    private String salt;  //盐
    private Date createTime;  //

    @NotNull(message="部门不能为空")
    private Long deptId;

    @TableField(exist=false)
    private String deptName;

    @TableField(exist=false)
    private List<Long> roleIdList;  //用户的角色ID列表

    private String mobile;
}
