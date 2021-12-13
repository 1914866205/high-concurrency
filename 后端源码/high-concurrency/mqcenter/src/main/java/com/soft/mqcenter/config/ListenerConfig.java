package com.soft.mqcenter.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.soft.mqcenter.common.Constants;
import com.soft.mqcenter.feignclient.ContentCenterFeignClient;
import com.soft.mqcenter.service.consumer.ConsumerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author 倪涛涛
 * @version 1.0.0
 * @ClassName ListenerConfig.java
 * @Description redis消息队列的监听者
 * @createTime 2021年04月05日 14:15:00
 */
@Configuration
public class ListenerConfig {
    @Resource
    private ContentCenterFeignClient contentCenterFeignClient;
    private List<String> goodId = null;

    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        goodId = contentCenterFeignClient.getAllGoodId();
        for (String goodId : goodId) {
            container.addMessageListener(listenerAdapter, new PatternTopic(Constants.REDIS_PRODUCT_PREFIX + goodId));
        }
        /**
         * 如果不定义线程池，每一次消费都会创建一个线程池，如果业务层不做限制，就会导致秒杀超卖
         */
        ThreadFactory factory = new ThreadFactoryBuilder()
                .setNameFormat("redis-listener-pool-%d").build();
        /**
         * 线程池创建的七个参数
         * @param corePoolSize 线程池核心线程大小
         * @param maximumPoolSize 线程最大线程数量
         * @param keepAliveTime 空闲线程存活时间
         * @param unit 空闲线程存活时间单位
         * @param workQueue 工作队列
         * @param threadFactory 线程工厂
         * @param handler 拒绝策略
         *                CallerRunsPolicy 直接拒绝
         *                AbortPolicy 直接丢弃任务并抛异常
         *                DiscardPolicy 直接丢弃任务，并且什么都不做
         *                DiscardOldestPolicy 抛弃进入队列最早的任务，然后尝试把这次拒绝的任务放入队列
         */
        Executor executor = new ThreadPoolExecutor(
                1,
                1,
                5L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(1000),
                factory);
        container.setTaskExecutor(executor);
        return container;
    }

    /**
     * 利用反射来创建监听到的消息之后执行方法
     *
     * @param consumerService
     * @return
     */
    @Bean
    MessageListenerAdapter listenerAdapter(ConsumerService consumerService) {
        return new MessageListenerAdapter(consumerService, "receiveMessage");
    }

    /**
     * 使用默认的工厂初始化redis操作模板
     *
     * @param connectionFactory
     * @return
     */
    @Bean
    StringRedisTemplate template(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

}
