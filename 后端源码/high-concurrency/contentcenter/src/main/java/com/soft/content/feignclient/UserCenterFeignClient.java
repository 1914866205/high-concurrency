package com.soft.content.feignclient;

import com.soft.content.common.ResponseResult;
//import com.soft.content.config.UserCenterFeignConfiguration;
import com.soft.content.config.UserCenterFeignConfiguration;
import com.soft.content.model.entity.HbUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName UserCenterFeignClient.java
 * @Description TODO
 * @createTime 2021年03月30日 14:52:00
 */
@FeignClient(name = "user-center")
public interface UserCenterFeignClient {
    /**
     * 根据用户id查询用户
     *
     * @param userId
     * @return
     */
    @GetMapping("/user/getInfoById/{userId}")
    ResponseResult findInfoById(@PathVariable(value = "userId") String userId);


    @PostMapping("user/edit")
    ResponseResult payOrder(@RequestBody HbUser hbUser);
}
