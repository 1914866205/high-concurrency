package com.soft.mqcenter.controller;

import com.soft.mqcenter.common.ResponseResult;
import com.soft.mqcenter.model.dto.OrderDto;
import com.soft.mqcenter.service.producer.ProducerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName RedisMQController.java
 * @Description TODO
 * @createTime 2021年04月05日 14:08:00
 */

@RestController
@RequestMapping
@Slf4j
@Api(value = "RedisMQController", tags = {"消息模块接口"})
public class RedisMQController {
    @Autowired
    private ProducerService producerService;
    private int number = 0;



    @PostMapping("/messageBatchToQueue")
    @ApiOperation(value = "批量上传消息到消息队列", notes = "批量上传消息到消息队列")
    public ResponseResult messageBatchToQueue(@RequestBody LinkedBlockingQueue<OrderDto> queue) {
        producerService.messageBatchToQueue(queue);
        return ResponseResult.success();
    }

}
/**
 * 1.前端万级并发请求秒杀接口
 * 2.秒杀接口把订单批量存储到消息队列
 * 3.消息队列批量读取数据
 * 4.消息队列把读取的数据转发到秒杀中心进行逻辑判断
 * 5.秒杀中心把结果发送到内容中心创建订单
 */