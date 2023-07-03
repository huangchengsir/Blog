
package com.huang.config;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huang.Utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;


//自定义拦截器
@Slf4j
@Component
public class DiyInterceptor implements HandlerInterceptor {

    @Autowired
    private JWTUtils jwtUtils;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("进入了拦截器方法");
        log.info(request.getRequestURI());
        //解决跨域问题
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        response.setHeader("Access-Control-Allow-Credentials", "true");

        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }
        String token =request.getHeader("Authorization");
        log.info(token);
        HashMap<String, Object> map = new HashMap<>();
        try{
            jwtUtils.verify(token);
            log.info("jwt验证通过");
            return true;
        } catch (TokenExpiredException e){
            map.put("state",false);
            map.put("msg", "Token已经过期，请重新登录");

            return false;
        } catch (SignatureVerificationException e){
            map.put("state",false);
            map.put("msg", "签名错误，请检查签名");

            return false;
        } catch (AlgorithmMismatchException e){
            map.put("state",false);
            map.put("msg", "加密算法不匹配！");

        } catch (Exception e){
            map.put("state",false);
            map.put("msg", "无效token，请重新登录！");
        }
        log.info(map.toString());
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        log.info("结束了拦截器方法");
        return false;
    }

}
