package com.huang.config;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


@Component
@Aspect
@Slf4j
public class LogAop {
    @Autowired
    HttpServletRequest request;

    //换行符
    private final static String LINE_SEPARATOR = System.lineSeparator();

    @Pointcut("execution(public * com.huang.controller..*.*(..))")
    public void apiPointCut() {
    }

    @Around("apiPointCut()")
    public Object apiAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        //打印出参
        log.info("Response Args  : {}", new Gson().toJson(result));
        //执行耗时
        log.info("Time-Consuming : {} ms",System.currentTimeMillis()-startTime,LINE_SEPARATOR);
        return result;
    }

    @Before("apiPointCut()")
    public void beforeReturning(JoinPoint joinPoint) {
        //开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //打印相关请求参数
        log.info("=============== Start ===============");
        // 打印请求 url
        log.info("URL            : {}", request.getRequestURL().toString());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP             : {}", request.getRemoteAddr());
        // 打印请求入参
        log.info("Request Args   : {}", new Gson().toJson(joinPoint.getArgs()));
    }


    @After("apiPointCut()")
    public void doAfter(){
//        接口结束后换行，方便分割查看
        log.info("=============== End ===============");
    }

}