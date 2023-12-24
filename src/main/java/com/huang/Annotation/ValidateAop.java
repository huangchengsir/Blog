package com.huang.Annotation;

import com.huang.Dto.LoginDto;
import com.huang.Utils.ChineseScan;
import com.huang.common.HttpsException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.FieldSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @program: Blog
 * @author: hjw
 * @create: 2023-12-20 22:50
 * @ClassName:ValidateAop
 * @Description:
 **/

@Component
@Aspect
@Slf4j
public class ValidateAop {
    public static void main(String[] args) {
        String test = "test";
        LoginDto loginDto = new LoginDto();
        System.out.println(loginDto.getClass());
    }
    private static final Set<Class<?>> PRIMITIVE_TYPES = new HashSet<>(Arrays.asList(
            Integer.class, int.class,
            Float.class, float.class,
            Double.class, double.class,
            Boolean.class, boolean.class,
            Character.class, char.class,
            String.class,
            BigDecimal.class,
            HttpServletResponse.class,HttpServletRequest.class));
    public static boolean isNotPrimitive(Object obj) {
        return obj != null && !PRIMITIVE_TYPES.contains(obj);
    }

    @Pointcut("@annotation(ParamCheck)")
    public void apiPointCut(){}

    @Before("apiPointCut()")
    public void BeforeRequest(JoinPoint joinPoint){
        if (joinPoint.getSignature() instanceof MethodSignature) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            Parameter[] parameters = method.getParameters();
            Object[] args = joinPoint.getArgs();
            for (Parameter parameter : parameters) {
                if (isNotPrimitive(parameter.getType())){
                    Arrays.stream(parameter.getType().getDeclaredFields())
                            .forEach(s->{
                                String paramName = s.getName();
                                s.setAccessible(true);
                                Optional.ofNullable(s.getAnnotation(SmartValidation.class))
                                        .ifPresent(tmp->{
                                            try {
                                                switch (tmp.type()){
                                                    case PASSWORD:
                                                        String validPassword = CheckMethod.isValidPassword(s.get(args[CheckMethod.getIndex(parameters, parameter)]).toString());
                                                        if(validPassword!=null){
                                                                throw new HttpsException(paramName+tmp.message()+":"+validPassword);
                                                            }
                                                        break;
                                                    case USERNAME:
                                                        String validUserName = CheckMethod.isValidUserName(s.get(args[CheckMethod.getIndex(parameters, parameter)]).toString());
                                                        if(validUserName!=null){
                                                                throw new HttpsException(paramName+tmp.message()+":"+validUserName);
                                                            }
                                                        break;
                                                    default:
                                                        break;
                                                }
                                            }catch (IllegalAccessException e) {
                                                throw new HttpsException(paramName+"参数校验失败");
                                            }
                                        });

                            });
                }else {
                    Optional.ofNullable(parameter.getAnnotation(SmartValidation.class))
                            .ifPresent(tmp->{
                                System.out.println(tmp.message());
                            });
                }

            }
        }
    }

    public static class CheckMethod{
        public static String isValidPassword(String password) {
            if(password != null && password.length() <= 7){
                return "密码最小长度必须大于等于7";
            }else {
                return null;
            }
        }
        public static String isValidUserName(String username) {
            if(ChineseScan.containsChinese(username)){
                return "用户名不能为空/中文";
            }else {
                return null;
            }
        }
        public static Integer getIndex(Parameter[] parameters,Parameter parameter) {
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].getName().equals(parameter.getName())) {
                    return i;
                }
            }
            return -1;
        }

    }
}
