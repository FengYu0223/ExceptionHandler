package com.fy.exception.controller.model;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program:  实体类
 * @description:
 * @author: Yu
 * @create: 2020-05-16 14:11
 **/
@Data
public class User {
    @NotBlank(message = "姓名不为空")
    private String username;
    @NotBlank(message = "密码不为空")
    private String password;
    // 嵌套必须加 @Valid，否则嵌套中的验证不生效
    @Valid
    @NotNull(message = "userinfo不能为空")
    private UserInfo userInfo;
}
