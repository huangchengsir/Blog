package com.huang.Utils;



import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.Calendar;
import java.util.Map;


@Data
@Component
@ConfigurationProperties(prefix = "jwtsetting")
@Slf4j
public class JWTUtils {
    private String secret;
    private int expire;
    /***
     * 生成token header.payload.secret
     */
    public String getToken(Map<String,String> map){

        Calendar instance=Calendar.getInstance();
        instance.add(Calendar.DATE,this.expire); //设置过期时间,yaml文件设置天数

        //创建jwt builder
        JWTCreator.Builder builder=JWT.create();

        //payload
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        String token = builder.withExpiresAt(instance.getTime())//指定令牌的过期时间
                .sign(Algorithm.HMAC256(this.secret));//签名算法

        return token;
    }

    /***
     * 验证token
     */
    public void verify(String token){
        JWT.require(Algorithm.HMAC256(this.secret)).build().verify(token);
    }

    /***
     * 获取token信息
     * 这个方法可用可不用
     */
    public DecodedJWT getTokenInfo(String token){
        DecodedJWT verify=JWT.require(Algorithm.HMAC256(this.secret)).build().verify(token);
        return verify;
    }
}
