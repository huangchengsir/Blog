package com.huang.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//mvc配置扩展类
@Configuration
public class Mvcconfig implements WebMvcConfigurer {

    @Autowired
    private DiyInterceptor diyInterceptor;

    /*
    * 若要将自己diy的类注册到容器中使用
    * @bean
    * 写一个返回类型为自身且return自身的类即可注入
    *
    @Bean
    public ViewResolver myViewResolver(){
        return new MyViewResolver();
    }
    * */
    //重写拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(diyInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("/**")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
