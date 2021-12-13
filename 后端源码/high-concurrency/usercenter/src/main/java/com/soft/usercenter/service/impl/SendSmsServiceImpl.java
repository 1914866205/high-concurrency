package com.soft.usercenter.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.soft.usercenter.common.ResponseResult;
import com.soft.usercenter.common.ResultCode;
import com.soft.usercenter.model.dto.SmsPhoneDto;
import com.soft.usercenter.model.dto.VerifyPhoneDto;
import com.soft.usercenter.service.SendSmsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Description TODO
 * @Author 涛涛
 * @Date 2020/5/21 10:26
 * @Version 1.0
 **/
@Slf4j
@Service
public class SendSmsServiceImpl implements SendSmsService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean send(String phoneNum, String templateCode, Map<String, Object> code) {

        //连接阿里云
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G3rkARQwiGGkprRZwGm", "Okx5ZZrlOveWo6WzQR3tHtCrDx5Mdq");
        IAcsClient client = new DefaultAcsClient(profile);
        //构建请求
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName", "智慧园区");
        request.putQueryParameter("TemplateCode", templateCode);
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(code));   //发送的验证码
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return true;
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean verify(VerifyPhoneDto verifyPhone) {
//        如果传来的验证码存在，通过验证
        log.info("传来的数据,verifyPhone,{}", verifyPhone + "**1**");
        log.info("缓存的验证码,verifyCode,{}", redisTemplate.opsForValue().get(verifyPhone.getPhoneNumber()) + "**1**");
        return verifyPhone.getVerifyCode().equals(redisTemplate.opsForValue().get(verifyPhone.getPhoneNumber()));
    }

    @Override
    public ResponseResult code(SmsPhoneDto smsPhoneDto) {
        String phoneNumber = smsPhoneDto.getPhoneNumber();
        //调用发送方法
        System.out.println("接收的phoneNumber" + phoneNumber);
        String code = redisTemplate.opsForValue().get(phoneNumber);
        if (!StringUtils.isEmpty(code)) {
            //数据已存在
            return ResponseResult.failure(ResultCode.DATA_ALREADY_EXISTED, phoneNumber + ":" + code + "已存在，还没有过期");
        }
        //生成验证码并存储到redis中
        code = UUID.randomUUID().toString().substring(0, 4);
        HashMap<String, Object> param = new HashMap<>();
        param.put("code", code);
        boolean sms = send(phoneNumber, "SMS_190277609", param);
        if (sms) {
            redisTemplate.opsForValue().set(phoneNumber, code, 5, TimeUnit.MINUTES);
            return ResponseResult.success(code);
        } else {
            return ResponseResult.failure(ResultCode.RESULT_CODE_DATA_NONE);
        }
    }
}
