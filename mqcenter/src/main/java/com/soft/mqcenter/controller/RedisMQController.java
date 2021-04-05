package com.soft.mqcenter.controller;

import com.soft.mqcenter.common.ResponseResult;
import com.soft.mqcenter.model.dto.OrderDto;
import com.soft.mqcenter.service.producer.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName RedisMQController.java
 * @Description TODO
 * @createTime 2021年04月05日 14:08:00
 */
@RestController
@RequestMapping()
public class RedisMQController {
    @Autowired
    private ProducerService producerService;

    @PostMapping("/messageToQueue")
    public ResponseResult messageToQueue(@RequestBody OrderDto orderDto) {
        producerService.sendMessage(orderDto);
        return ResponseResult.success();
    }
}
