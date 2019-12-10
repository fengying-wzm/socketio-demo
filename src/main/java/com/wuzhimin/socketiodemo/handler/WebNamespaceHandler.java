package com.wuzhimin.socketiodemo.handler;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 命名空间/web的处理器
 */
@Component
@Slf4j
public class WebNamespaceHandler {


    private final SocketIONamespace socketIONamespace;

    @Autowired
    private ClientManager clientManager;

    public static final String NAMESPACE_WEB = "/web";

    @Autowired
    private WebNamespaceHandler(SocketIOServer socketIOServer) {
        this.socketIONamespace = socketIOServer.addNamespace(NAMESPACE_WEB);
        this.socketIONamespace.addListeners(this);
    }

    /**
     * 客户端连接的时候触发
     *
     * @param client
     */
    @OnConnect
    public void onConnect(SocketIOClient client) {

        log.info("客户端已连接:{},命名空间:{}", client.getSessionId(), client.getNamespace());

        clientManager.addClient(client, socketIONamespace);
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
