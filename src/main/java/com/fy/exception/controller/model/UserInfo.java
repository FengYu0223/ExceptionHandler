package com.fy.exception.controller.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

/**
 * @program:
 * @description:
 * @author: Yu
 * @create: 2020-05-16 14:11
 **/
@Data
public class UserInfo {
    @NotBlank(message = "年龄不为空")
    @Max(value = 18, message = "不能超过18岁")
    private String age;
    @NotBlank(message = "性别不能为空")
    private String gender;
}
