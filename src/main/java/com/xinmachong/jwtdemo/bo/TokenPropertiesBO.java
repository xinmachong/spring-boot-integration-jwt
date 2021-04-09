package com.xinmachong.jwtdemo.bo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author meyer@HongYe
 */
@Component
public class TokenPropertiesBO {
    private Integer tokenExpire;
    private String tokenSignature;

    public Integer getTokenExpire() {
        return tokenExpire;
    }

    @Value("${mydefine.token.expire}")
    public void setTokenExpire(Integer tokenExpire) {
        this.tokenExpire = tokenExpire;
    }


    public String getTokenSignature() {
        return tokenSignature;
    }

    @Value("${mydefine.token.signature}")
    public void setTokenSignature(String tokenSignature) {
        this.tokenSignature = tokenSignature;
    }
}