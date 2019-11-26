package com.wuzhimin.socketiodemo.handler;

import com.corundumstudio.socketio.AuthorizationListener;
import com.corundumstudio.socketio.HandshakeData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthHandler implements AuthorizationListener {
    @Override
    public boolean isAuthorized(HandshakeData handshakeData) {
        // 对token进行鉴权
        String token = handshakeData.getSingleUrlParam("token");
        if(token!=null&&token.length()>0){
            log.error("鉴权通过，token="+token);
            return true;
        }
        log.error("鉴权不通过");
        return false;
    }
}
