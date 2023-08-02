package com.huang.config.shiro;


import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("security") DefaultWebSecurityManager security){
        ShiroFilterFactoryBean filter = new ShiroFilterFactoryBean();
        /*      * 权限设置
        * anon：无需认证即可访问
        * authc：必须认证才可以访问
        * user：必须拥有“记住我”才能用
        * perms：对某个资源的权限
        * role：角色权限
        * */

        //设置自定义过滤器到shiro过滤链中
        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("authc",jwtFilter);
        filter.setFilters(filters);
        //这里对页面请求进行权限设置，用户授权在realme里面进行
        //内置过滤器默认接受map对象，且为链式操作，所以使用LinkedHashMap\
        Map<String, String> map = new LinkedHashMap<>();
        //授权
        map.put("/logout", "authc");
        map.put("/blogs", "authc");
        map.put("/blog/{id}", "authc");
        map.put("/blog/edit", "authc");
        map.put("/blogs/delete", "authc");
        map.put("/login","anon");
        map.put("/user/register","anon");
//        map.put("/**", "authc");
        filter.setFilterChainDefinitionMap(map);
        //设置登录页
//        filter.setLoginUrl("/login");
        //设置授权成功后页面，一般不用
//        factoryBean.setSuccessUrl("");
        //设置权限验证失败后页面
//        factoryBean.setUnauthorizedUrl("");
        filter.setSecurityManager(security);
        //返回factoryBean时才会生效
        return filter;

    }

    @Bean(name="security")
    public DefaultWebSecurityManager defaultWebSecurityManager(@Qualifier("realme") Realme realme){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realme);
        return securityManager;
    }

    @Bean
    public Realme realme(){
        return new Realme();
    }
}
