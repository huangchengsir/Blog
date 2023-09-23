package com.huang.config;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: clothing-applet
 * @author: hjw
 * @create: 2023-09-21 20:30
 * @ClassName:HttpsConfig
 * @Description: https配置，将http请求全部转发到https
 **/

@Configuration
public class HttpsConfig {

    @Value("${custom.http-port}")
    private Integer httpPort;

    @Value("${server.port}")
    private Integer port;

    @Value("${server.ssl.enabled}")
    private Boolean status;

    /**
    * @ParamType: []
    * @param
    * @return: TomcatServletWebServerFactory
    * @Author: hjw
    * @Date: 20:31 2023/9/21
    * @Description:
    */
    @Bean
    public TomcatServletWebServerFactory servletContainer() {
        // 将http请求转换为https请求
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint constraint = new SecurityConstraint();
                // 默认为NONE
                if(status){
                    constraint.setUserConstraint("CONFIDENTIAL");
                }
                SecurityCollection collection = new SecurityCollection();
                // 所有的东西都https
                collection.addPattern("/*");
                constraint.addCollection(collection);
                context.addConstraint(constraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(httpConnector());
        return tomcat;
    }

    /**
     * 强制将所有的http请求转发到https
     *
     * @return httpConnector
     */
    @Bean
    public Connector httpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        // connector监听的http端口号
        connector.setPort(httpPort);
        connector.setSecure(false);
        if(status){
                // 监听到http的端口号后转向到的https的端口号
                connector.setRedirectPort(port);
        }
        return connector;
    }

}
