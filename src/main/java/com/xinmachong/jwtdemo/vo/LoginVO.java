package com.xinmachong.jwtdemo.vo;

import lombok.Data;

/**
 * @Author meyer@HongYe
 */
@Data
public class LoginVO {
    private String token;
    private String username;
    private String avatar;
    private boolean admin;
}
