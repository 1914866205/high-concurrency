package com.soft.usercenter.service.impl;

import com.soft.usercenter.common.ResponseResult;
import com.soft.usercenter.common.ResultCode;
import com.soft.usercenter.model.dto.LoginDto;
import com.soft.usercenter.model.dto.VerifyPhoneDto;
import com.soft.usercenter.model.entity.HbUser;
import com.soft.usercenter.repository.HbUserRepository;
import com.soft.usercenter.service.HbUserService;
import com.soft.usercenter.service.SendSmsService;
import com.soft.usercenter.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbUserServiceImpl.java
 * @Description TODO
 * @createTime 2021年03月27日 09:48:00
 */
@Service
@Slf4j
public class HbUserServiceImpl implements HbUserService {
    @Resource
    private HbUserRepository hbUserRepository;
    @Resource
    private SendSmsService sendSmsService;

    @Override
    public ResponseResult login(LoginDto loginDto) throws UnsupportedEncodingException {
        log.info("loginDto:" + loginDto.getUsername() + "*********" + loginDto.getPassword());
        //判断账号是否存在
        HbUser hbUser = hbUserRepository.findHbUserByUsername(loginDto.getUsername());
        if (hbUser != null) {
            //如果查到数据，返回用户数据
            if (loginDto.getPassword().equals(hbUser.getPassword())) {
                log.info("登录成功");
                //创建返回的数据
                Map map = new HashMap();
                map.put("user", hbUser);
                map.put("token", JwtUtil.sign(loginDto.getUsername(), loginDto.getPassword()));
                log.info("生成的token{}", map.get("token"));
                return ResponseResult.success(map);
            }
            return ResponseResult.failure(ResultCode.USER_ACCOUNT_PASSWORD_ERROR);
        } else {
            return ResponseResult.failure(ResultCode.USER_NOT_FOUND);
        }

    }


    @Override
    public ResponseResult loginByPhone(VerifyPhoneDto verifyPhone) throws UnsupportedEncodingException {
        log.info("verifyPhone:" + verifyPhone.getPhoneNumber() + "*********" + verifyPhone.getVerifyCode());
        //判断验证码是否正确
        HbUser hbUser = hbUserRepository.findHbUserByPhone(verifyPhone.getPhoneNumber());
        if (hbUser != null) {
            //如果查到数据，则判断验证码是否正确
            if (sendSmsService.verify(verifyPhone)) {
//                验证码通过
                log.info("登录成功");
                //创建返回的数据
                Map map = new HashMap();
                map.put("user", hbUser);
                map.put("token", JwtUtil.sign(hbUser.getUsername(), hbUser.getPassword()));
                log.info("生成的token{}", map.get("token"));
                return ResponseResult.success(map);
            } else {
                //验证码失效或错误
                return ResponseResult.failure(ResultCode.USER_VERIFY_CODE_ERROR_TIMEOUT);
            }
        } else {
            return ResponseResult.failure(ResultCode.USER_NOT_FOUND);
        }
    }
}
