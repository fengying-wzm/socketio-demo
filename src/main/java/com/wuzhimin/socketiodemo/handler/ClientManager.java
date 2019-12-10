package com.wuzhimin.socketiodemo.handler;

import com.corundumstudio.socketio.BroadcastOperations;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 客户端处理
 */
@Component
@Slf4j
public class ClientManager {


    /**
     * 添加client到room，使用广播的方式同步到其他server副本
     * @param socketIOClient
     * @param socketIONamespace
     */
    public void addClient(SocketIOClient socketIOClient, SocketIONamespace socketIONamespace){
        String userId = socketIOClient.getHandshakeData().getSingleUrlParam("userId");
        String room = "USER_"+userId;
        //加入用户的房间，实现单设备登录
        socketIOClient.joinRoom(room);

        //发送强制登出信息给其他已经登录的客户端，注意要除了当前的client
        BroadcastOperations broadcastOperations = socketIONamespace.getRoomOperations(room);
        if(broadcastOperations!=null){
            log.info("发送退出登录消息");
            broadcastOperations.sendEvent("accountevent", socketIOClient, "赶紧退出登录吧");
        }

    }


}

