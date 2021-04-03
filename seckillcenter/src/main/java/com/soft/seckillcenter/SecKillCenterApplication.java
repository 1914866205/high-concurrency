package com.soft.seckillcenter;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.client.RestTemplate;


/**
 * @author nitaotao
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.soft.seckillcenter.feignclient"})
public class SecKillCenterApplication {

    public static void main(String[] args) {
        //测试redis是否连接成功
//          Jedis jedis = new Jedis("120.26.177.51", 6379);
//        System.out.println(jedis.ping());
        SpringApplication.run(SecKillCenterApplication.class, args);
    }
    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @LoadBalanced
    public AsyncRestTemplate asyncRestTemplate() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        //设置链接超时时间
        factory.setConnectTimeout(100);
        //设置读取资料超时时间
        factory.setReadTimeout(200);
        //设置异步任务（线程不会重用，每次调用时都会重新启动一个新的线程
        factory.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return new AsyncRestTemplate(factory);
    }

    @Bean
    public Redisson redisson() {
        //单机模式
        Config config = new Config();
        config.useSingleServer().setAddress("redis://120.26.177.51:6379").setDatabase(0);
        return (Redisson) Redisson.create(config);
    }
}
