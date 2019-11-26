package com.wuzhimin.socketiodemo.handler;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.wuzhimin.socketiodemo.dto.Account;
import com.wuzhimin.socketiodemo.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MessageEventHandler {


    /**
     * 客户端事件
     *
     * @param client  　客户端信息
     * @param request 请求信息
     * @param data    　客户端发送数据
     */
    @OnEvent(value = "messageevent")
    public void onEvent(SocketIOClient client, AckRequest request, Message data) {
        log.info("发来消息：" + data);
        //回发消息
        client.sendEvent("messageevent", "我是服务器都安发送的信息");
        //广播消息
        sendBroadcast();
    }



    /**
     * 客户端事件
     *
     * @param client  　客户端信息
     * @param request 请求信息
     * @param data    　客户端发送数据
     */
    @OnEvent(value = "accountevent")
    public void onEvent(SocketIOClient client, AckRequest request, Account data) {
        log.info("发来消息：" + data);
        //回发消息
        client.sendEvent("accountevent", "你的账号没问题");
        //广播消息
        sendBroadcast();
    }


    /**
     * 广播消息
     */
    public void sendBroadcast() {
        for (SocketIOClient client : ConnectEventHandler.socketIOClientMap.values()) {
            if (client.isChannelOpen()) {
                client.sendEvent("Broadcast", "当前时间", System.currentTimeMillis());
            }
        }

    }
}
