package com.wuzhimin.socketiodemo.config;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.SpringAnnotationScanner;
import com.wuzhimin.socketiodemo.handler.AuthHandler;
import lombok.extern.slf4j.Slf4j;
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

    @Autowired
    private AuthHandler authHandler;

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

        SocketIOServer server = new SocketIOServer(config);
        return server;
    }

    /**
     * 用于扫描netty-socketio的注解，比如 @OnConnect、@OnEvent
     */
    @Bean
    public SpringAnnotationScanner springAnnotationScanner() {
        return new SpringAnnotationScanner(socketIOServer());
    }
}