package com.heima.schedule.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private String port;
    @Value("${spring.redis.password}")
    private String password;


    @Bean
    public RedissonClient redissonClient(){
        Config config = new Config();
       // System.out.println(host+":"+port+"密码:"+password);
        config.useSingleServer().setAddress("redis://"+host+":"+port).setPassword(password).setDatabase(0);
        return Redisson.create(config);
    }




}
