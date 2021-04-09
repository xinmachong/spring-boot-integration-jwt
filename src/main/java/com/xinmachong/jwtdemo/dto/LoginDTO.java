package com.xinmachong.jwtdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Author meyer@HongYe
 */
@Data
@NoArgsConstructor
public class LoginDTO {
    @NotBlank(message = "用户名不能为空")
    private String account;
    @NotBlank(message = "密码不能为空")
    private String password;
}
