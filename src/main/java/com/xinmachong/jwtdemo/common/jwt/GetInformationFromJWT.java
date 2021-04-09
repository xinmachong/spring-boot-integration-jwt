package com.xinmachong.jwtdemo.common.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.xinmachong.jwtdemo.utils.JWTUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author meyer@HongYe
 */
@Component
public class GetInformationFromJWT {

    public int getUserIdByJWT(HttpServletRequest request){
        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtils.verify(token);
        return Integer.parseInt(verify.getClaim("userId").asString());
    }

    public int getRoleIdByJWT(HttpServletRequest request){
        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtils.verify(token);
        return Integer.parseInt(verify.getClaim("roleId").asString());
    }

    public int getGroupIdByJWT(HttpServletRequest request){
        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtils.verify(token);
        return Integer.parseInt(verify.getClaim("groupId").asString());
    }

    public int getClinicIdByJWT(HttpServletRequest request){
        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtils.verify(token);
        return Integer.parseInt(verify.getClaim("clinicId").asString());
    }

    public String getUsernameByJWT(HttpServletRequest request){
        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtils.verify(token);
        return verify.getClaim("username").asString();
    }
}
