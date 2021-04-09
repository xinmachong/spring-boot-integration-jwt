package com.xinmachong.jwtdemo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * @Author meyer@HongYe
 */
@Data
@NoArgsConstructor
public class CreateUserDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "工号不能为空")
    private String staffNo;
    private String avatar;
}
