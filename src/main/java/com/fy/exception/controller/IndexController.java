package com.fy.exception.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.fy.exception.common.constant.enums.ArgumentResponseEnum;
import com.fy.exception.common.constant.enums.CommonResponseEnum;
import com.fy.exception.common.exception.ArgumentException;
import com.fy.exception.common.exception.BusinessException;
import com.fy.exception.common.model.response.BaseResponse;
import com.fy.exception.controller.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;

/**
 * @description: 异常处理示例
 * @author: Yu
 * @create: 2020-05-16 11:45
 **/
@RestController
@RequestMapping(value = "/api")
@Slf4j
public class IndexController {

    //z注入一个 RestTemplate 的bean
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private RestTemplate restTemplate;


    /**
     * 模式一 ，用@Valid校验 post请求的json字符串，
     *
     * @param user User
     * @return ResponseResult
     */
    @PostMapping("/user")
    public BaseResponse addUserInfo(@Valid @RequestBody User user) {

        // do someting 具体业务逻辑...
        return new BaseResponse(CommonResponseEnum.SUCCESS);
    }

    /**
     * 模式二，对入参字符串username 进行校验：用枚举类和断言，判断入参校验，如果进入断言，则抛出异常
     * （省去过多的if else，ps:assertNotEmpty方法简单的非空校验，如果业务校验比较多（比如，非空、非法字符等校验，则新建一个校验方法即可））
     * 模式三，对第三方接口做一个异常处理，包一层try语句，请求超时或者连接失败的时候抛出异常，对请求接口返回结果做一个简单校验
     *
     * @param username 入参
     * @return ResponseResult
     */
    @Validated
    @GetMapping("/user/{username}")
    public BaseResponse findUserInfo(@PathVariable String username) {
        //模式二
        ArgumentResponseEnum.VALID_ERROR.assertNotEmpty(username);

        //模式三
        String result;
        try {

            //写一个假域名，为了抛出连接超时
            result = restTemplate.getForObject("http:www.baidu.comccccc", String.class);

        } catch (ArgumentException e) {
            log.error("ArgumentException :", e);
            throw new BusinessException(CommonResponseEnum.SERVER_BUSY, null, "");
        }
        //对 接口返回 做一个简单校验，如果返回不合法，则抛出异常（请求接口的锅）
        CommonResponseEnum.SERVER_BUSY.assertIsTrue(isValidate(result));

        //do something 业务逻辑...

        return new BaseResponse(CommonResponseEnum.SUCCESS);
    }


    /**
     * 校验字符串是否是json格式
     *
     * @param str String
     * @return boolean
     */
    public boolean isValidate(String str) {
        try {
            JSON.parse(str);
            return true;
        } catch (JSONException e) {
            return false;
        }
    }

}
