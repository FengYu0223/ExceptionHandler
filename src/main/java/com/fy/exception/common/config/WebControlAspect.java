package com.fy.exception.common.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @program: 切面程序日志，记录接口入参请求情况
 * @description:
 * @author: Yu
 * @create: 2020-05-16 11:46
 **/
@Aspect
@Component
@Slf4j
public class WebControlAspect {

    ThreadLocal<Long> startTime = new ThreadLocal<>();  //线程副本类去记录各个线程的开始时间


    /**
     * 1、execution 表达式主体
     * 2、第1个* 表示返回值类型  *表示所有类型
     * 3、包名  com.*.*.controller下
     * 4、第4个* 类名，com.*.*.controller包下所有类
     * 5、第5个* 方法名，com.*.*.controller包下所有类所有方法
     * 6、(..) 表示方法参数，..表示任何参数
     */
    @Pointcut("execution(public * com.fy.exception.controller..*.*(..))")
    public void controllerLog() {
    }

    @Before("controllerLog()")
    public void logBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());

        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();//这个RequestContextHolder是Springmvc提供来获得请求的东西
        HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

        // 记录下请求内容
        log.info("【#### URL】[{}] ", request.getRequestURL().toString());
        log.info("【#### HTTP_METHOD】[{}] ", request.getMethod());
        log.info("【#### IP】[{}]", request.getRemoteAddr());
        log.info("【#### ARGS 】[{}] ", Arrays.toString(joinPoint.getArgs())); // 方法本传了哪些参数
        //下面这个getSignature().getDeclaringTypeName()是获取包+类名的   然后后面的joinPoint.getSignature.getName()获取了方法名
        log.info("【#### LASS_METHOD】[{}]" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());

        //TODO 也可以在这里对 请求 token 或者请求统一校验
        //TODO 或者记录请求情况，保存道数据库，以便做埋点，或者异常日志查看

    }


    //方法的返回值注入给ret
    @AfterReturning(returning = "ret", pointcut = "controllerLog()")
    public void logBAfter(Object ret) {
        log.info("【##### RESPONSE】[{}]", JSON.toJSONString(ret));       // 响应的内容---方法的返回值responseEntity
        log.info("【##### SPEND】[{}]ms", (System.currentTimeMillis() - startTime.get()));

        startTime.remove(); //释放ThreadLocal
    }

}
