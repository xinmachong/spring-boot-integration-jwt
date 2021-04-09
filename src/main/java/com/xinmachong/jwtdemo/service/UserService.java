package com.xinmachong.jwtdemo.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinmachong.jwtdemo.bean.User;
import com.xinmachong.jwtdemo.common.jwt.GetInformationFromJWT;
import com.xinmachong.jwtdemo.dto.CreateUserDTO;
import com.xinmachong.jwtdemo.dto.LoginDTO;
import com.xinmachong.jwtdemo.mapper.UserMapper;
import com.xinmachong.jwtdemo.utils.JWTUtils;
import com.xinmachong.jwtdemo.vo.LoginVO;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @Author meyer@HongYe
 */
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private JWTUtils jwtUtils;
    @Resource
    private GetInformationFromJWT getInformationFromJWT;


    @Transactional
    public String getSaltByAccount(LoginDTO loginDTO) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getAccount,loginDTO.getAccount());
        return this.userMapper.selectOne(wrapper).getSalt();
    }

    public String getPasswordByAccount(LoginDTO loginDTO) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getAccount,loginDTO.getAccount());
        return this.userMapper.selectOne(wrapper).getPassword();
    }

    public LoginVO getUserMsg(LoginDTO loginDTO) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getAccount,loginDTO.getAccount());
        User meyerUser = this.userMapper.selectOne(wrapper);
        String token = this.getToken(meyerUser);
        boolean admin = false;
        if (meyerUser.getAdmin() == 1) {
            admin = true;
        }

        LoginVO loginVO = new LoginVO();
        loginVO.setToken(token);
        loginVO.setAdmin(admin);
        BeanUtils.copyProperties(meyerUser,loginVO);
        return loginVO;
    }

    public String getToken(User meyerUser) {
        Map<String,String> payload = new HashMap<>();
        payload.put("userId", meyerUser.getId()+"");
        payload.put("username", meyerUser.getUsername());
        payload.put("account", meyerUser.getAccount());
        return JWTUtils.getToken(payload);
    }

    public boolean checkStaffNoIsExist(CreateUserDTO createUserDTO, HttpServletRequest request) {
        int userId = this.getInformationFromJWT.getUserIdByJWT(request);
        System.out.println("userId = " + userId);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getStaffNo,createUserDTO.getStaffNo());
        return this.userMapper.selectCount(wrapper) > 0;
    }

    @Transactional
    public void addUser(CreateUserDTO createUserDTO) {
        String salt = UUID.randomUUID().toString().substring(0,4);
        String encodedPassword = new SimpleHash("MD5","123456",salt,1).toHex();
        User user = new User();
        BeanUtils.copyProperties(createUserDTO,user);
        user.setAccount(createUserDTO.getStaffNo()).setSalt(salt).setPassword(encodedPassword);
        this.userMapper.insert(user);
    }
}
