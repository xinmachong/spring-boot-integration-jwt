package com.xinmachong.jwtdemo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xinmachong.jwtdemo.bo.TokenPropertiesBO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

/**
 * @Author meyer@HongYe
 */
@Component
public class JWTUtils {

//    @Value("${mydefine.token.expire}")
//    private int expire;
//    @Value("${mydefine.token.signature}")
//    private String signature;

    @Resource
    private TokenPropertiesBO initTokenPropertiesBO;
    private static TokenPropertiesBO tokenPropertiesBO;

    //@PostConstruct 意思就是在完成构造函数实例化后就调用该方法，该方法会对 TokenPropertiesBO 对象实例化。
    @PostConstruct
    public void init(){
        tokenPropertiesBO = initTokenPropertiesBO;
    }


    /**
     * 生成 token
     */
    public static String getToken(Map<String,String> map) {
        Date date = new Date(System.currentTimeMillis() + tokenPropertiesBO.getTokenExpire()*1000);
        Algorithm algorithm = Algorithm.HMAC256(tokenPropertiesBO.getTokenSignature());
        //创建jwt builder
        JWTCreator.Builder builder = JWT.create();
        //payload
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        // 附带username信息
        return builder
                //到期时间
                .withExpiresAt(date)
                //创建一个新的JWT，并使用给定的算法进行标记
                .sign(algorithm);
    }

    /**
     * 校验 token 是否正确
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(tokenPropertiesBO.getTokenSignature())).build().verify(token);
    }

    /**
     * 获得token中的信息，无需secret解密也能获得
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }


    /**
     * 获得token中的信息，无需secret解密也能获得
     */
    public static String getRoleType(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("roleType").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
