package com.soft.usercenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import redis.clients.jedis.Jedis;

/**
 * @author nitaotao
 */

@SpringBootApplication
public class UserCenterApplication {

    public static void main(String[] args) {
        //测试redis是否连接成功
//          Jedis jedis = new Jedis("120.26.177.51", 6379);
//        System.out.println(jedis.ping());
        SpringApplication.run(UserCenterApplication.class, args);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
