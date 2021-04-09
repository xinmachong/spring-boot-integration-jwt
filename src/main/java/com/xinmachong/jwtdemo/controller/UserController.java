package com.xinmachong.jwtdemo.controller;

import com.xinmachong.jwtdemo.common.response.ApiResponse;
import com.xinmachong.jwtdemo.dto.CreateUserDTO;
import com.xinmachong.jwtdemo.dto.LoginDTO;
import com.xinmachong.jwtdemo.service.UserService;
import com.xinmachong.jwtdemo.vo.LoginVO;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author meyer@HongYe
 */
@RestController
@RequestMapping("/v1/user")
@Validated
@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;


    @PostMapping("/login")
    public ApiResponse login(@RequestBody @Validated LoginDTO loginDTO) {
        String salt;
        try {
            salt = this.userService.getSaltByAccount(loginDTO);
        } catch (Exception e) {
            return new ApiResponse(400,"账号不存在",0);
        }
        String password = new SimpleHash("MD5",loginDTO.getPassword(),salt,1).toHex();
        String realPassword = this.userService.getPasswordByAccount(loginDTO);
        if(StringUtils.isEmpty(realPassword)){
            return new ApiResponse(400,"用户名错误",0);
        } else if (!realPassword.equals(password)) {
            return new ApiResponse(400, "密码错误", 0);
        } else {
            LoginVO loginVO = this.userService.getUserMsg(loginDTO);
            return new ApiResponse(200,"success",loginVO);
        }
    }


    @PostMapping("/add")
    public ApiResponse add(@RequestBody @Validated CreateUserDTO createUserDTO, HttpServletRequest request) {
        boolean isExistStaffNo = this.userService.checkStaffNoIsExist(createUserDTO,request);
        if (isExistStaffNo) {
            return new ApiResponse(400,"工号已存在，请勿重复添加",0);
        }
        try {
            this.userService.addUser(createUserDTO);
            return new ApiResponse(200, "success", 1);
        } catch (Exception e) {
            return new ApiResponse(400,"用户添加失败",0);
        }
    }
}
