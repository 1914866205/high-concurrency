package com.soft.mqcenter.service.consumer;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ConsumerService.java
 * @Description TODO
 * @createTime 2021年04月05日 14:14:00
 */
public interface ConsumerService {
    void receiveMessage(String message);
}
