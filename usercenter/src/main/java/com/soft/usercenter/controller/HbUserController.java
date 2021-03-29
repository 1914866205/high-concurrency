package com.soft.usercenter.controller;

import com.soft.usercenter.common.ResponseResult;
import com.soft.usercenter.model.dto.LoginDto;
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
@RequestMapping(value="/user")
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
}
