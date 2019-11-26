package com.wuzhimin.socketiodemo.handler;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
@Slf4j
public class ConnectEventHandler {


    public static ConcurrentMap<String, SocketIOClient> socketIOClientMap = new ConcurrentHashMap<>();

    /**
     * 客户端连接的时候触发
     *
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {
        String mac = client.getHandshakeData().getSingleUrlParam("mac");
        client.getHandshakeData().getHttpHeaders();
        //存储SocketIOClient，用于发送消息
        socketIOClientMap.put(mac, client);
        //回发消息
        client.sendEvent("message", "onConnect back");
        log.info("客户端:" + client.getSessionId() + "已连接,mac=" + mac);
    }


    /**
     * 客户端关闭连接时触发
     *
     * @param client
     */
    @OnDisconnect
    public void onDisconnect(SocketIOClient client) {
        log.info("客户端:" + client.getSessionId() + "断开连接");
    }

}
