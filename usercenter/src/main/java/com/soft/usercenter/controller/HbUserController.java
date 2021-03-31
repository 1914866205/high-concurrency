package com.soft.usercenter.controller;

import com.soft.usercenter.annotation.ControllerWebLog;
import com.soft.usercenter.common.ResponseResult;
import com.soft.usercenter.model.dto.EditUserDto;
import com.soft.usercenter.model.dto.LoginDto;
import com.soft.usercenter.model.dto.RegisterUserDto;
import com.soft.usercenter.model.dto.VerifyPhoneDto;
import com.soft.usercenter.service.HbUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName HbUserController.java
 * @Description TODO
 * @createTime 2021年03月27日 09:52:00
 */
@RestController
@Slf4j
@ResponseBody
@RequestMapping(value = "/user")
@Api(value = "HbUserController", tags = {"用户模块接口"})
public class HbUserController {

    @Resource
    private HbUserService hbUserService;

    /**
     * 账号密码登录
     *
     * @param loginDto
     * @return
     * @throws UnsupportedEncodingException
     */
    @ApiOperation(value = "登录", notes = "账号密码登录")
    @PostMapping("login")
    public ResponseResult login(@RequestBody LoginDto loginDto) throws UnsupportedEncodingException {
        log.info("访问login接口");
        log.info("loginDto{}", loginDto);
        return hbUserService.login(loginDto);
    }

    /**
     * 根据用户ID得到用户信息
     * @param userId
     * @return
     */
    @ApiOperation(value = "根据用户ID得到用户信息", notes = "根据用户ID得到用户信息")
    @GetMapping("getInfoById/{userId}")
    public ResponseResult getInfoById(@PathVariable String userId) {
        log.info("访问getInfoById接口");
        log.info("userId{}", userId);
        return hbUserService.getInfoById(userId);
    }



    @ControllerWebLog(name = "loginByPhone", isSaved = true)
    @ApiOperation(value = "手机验证码登录", notes = "请求参数为手机号 和  手机验证码 phoneNumber   verifyCode   ")
    @PostMapping("code/login")
    public ResponseResult loginByPhone(@RequestBody VerifyPhoneDto verifyPhone) throws UnsupportedEncodingException {
        log.info("访问code/login接口");
        log.info("-----code/login-----请求参数：" + verifyPhone + "**1**");
        return hbUserService.loginByPhone(verifyPhone);
    }

    @ControllerWebLog(name = "userRegister", isSaved = true)
    @ApiOperation(value = "用户注册", notes = "用户注册")//birthday时间传的形式为  2021-03-29 形式
    @PostMapping("register")
    public ResponseResult registerUser(@RequestBody RegisterUserDto registerUserDto) throws UnsupportedEncodingException {
        System.out.println(registerUserDto.toString());
        return hbUserService.register(registerUserDto);
    }


    @ControllerWebLog(name = "userEdit", isSaved = true)
    @ApiOperation(value = "用户信息修改", notes = "用户信息修改")//birthday时间传的形式为  2021-03-29 形式
    @PostMapping("edit")
    public ResponseResult editUser(@RequestBody EditUserDto editUserDto) throws UnsupportedEncodingException {
        System.out.println(editUserDto.toString());
        return hbUserService.edit(editUserDto);
    }


}
