package com.wuzhimin.socketiodemo.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.store.RedissonStoreFactory;
import com.wuzhimin.socketiodemo.handler.AuthHandler;
import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class NettySocketioConfig {

    @Value("${socketio.hostname}")
    private String hostname;

    @Value("${socketio.port}")
    private int port;


    @Value("${redis.host}")
    private String redisHost;


    @Value("${redis.password}")
    private String redisPassword;

    @Value("${redis.port}")
    private int redisPort;

    @Autowired
    private AuthHandler authHandler;

    @Autowired
    private RedissonClient redissonClient;

    /**
     * netty-socketio服务器
     */
    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        log.info("socket地址 - > "+hostname+":"+port);
        config.setHostname(hostname);
        config.setPort(port);
        config.setPingInterval(1000);
        config.setPingTimeout(5000);
        config.setAuthorizationListener(authHandler);

        //使用redis的pub/sub实现多副本session同步
        config.setStoreFactory(new RedissonStoreFactory(redissonClient));

        SocketIOServer server = new SocketIOServer(config);

        return server;
    }

    @Bean
    public RedissonClient getRedissonClient(){
        Config config = new Config();
        config.useSingleServer().setPassword(redisPassword).setAddress("redis://"+redisHost+":"+redisPort);
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }


}