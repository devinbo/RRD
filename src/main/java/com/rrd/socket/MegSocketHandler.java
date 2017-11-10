package com.rrd.socket;

import com.rrd.dao.BaseDao;
import com.rrd.model.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;

/**
 * Created by xinghb on 2017/10/28.
 */
@Component
public class MegSocketHandler implements WebSocketHandler{
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MegSocketHandler.class);

    private static Map<Long, WebSocketSession> userMaps = new HashMap<>();
    private static Map<String, Set<Long>> auth = new HashMap<>();

    @Autowired
    private BaseDao baseDao;

    @PostConstruct
    public void initAuthData() {
        List<Map<String, Object>> list = baseDao.getAllPullAuth();
        for (Map<String, Object> map : list) {
            auth.put(map.get("data_code").toString(), new HashSet<>());
        }
    }

    public static void updateAuthData(Long userid, List<String> authIds) {
        for (String authid : authIds) {
            Set<Long> list = auth.get(authid);
            if(!list.contains(userid)) {
                list.add(userid);
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        User user = (User) webSocketSession.getAttributes().get("user");
        userMaps.put(user.getId(), webSocketSession);
        String tzauth = user.getTzauth();
        if(!StringUtils.isEmpty(tzauth)) {
            String[] authIds = tzauth.split(",");
            for (String authid : authIds) {
                Set<Long> authList = auth.get(authid);
                authList.add(user.getId());
            }
        }
    }

    /**
     * 接收消息处理器
     * @param webSocketSession
     * @param webSocketMessage
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        System.out.println("webSocketSession = [" + webSocketSession + "], webSocketMessage = [" + webSocketMessage + "]");
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        System.out.println("MegSocketHandler.handleTransportError");
        logger.error(throwable);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        //断开连接的时候，那么移除用户
        User user = (User) webSocketSession.getAttributes().get("user");
        userMaps.remove(user.getId());
        for (Set<Long> sets : auth.values()) {
            sets.remove(user.getId());
        }
    }

    @Override
    public boolean supportsPartialMessages() {
        System.out.println("MegSocketHandler.supportsPartialMessages1");
        return false;
    }

    /**
     * 发送消息给拥有指定权限的用户
     */
    public static void sendUserMegWithAuth(WebSocketMessage<?> message, String id) {
        Set<Long> userIds = auth.get(id);
        for (Long userid : userIds) {
            sendOneUserMeg(message, userid);
        }
    }

    public static void sendOneUserMeg(WebSocketMessage<?> message, Long userid) {
        WebSocketSession session = userMaps.get(userid);
        try {
            session.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error(e);
        }
    }
}
