package com.example.springws.handler;


import org.springframework.web.socket.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * @author 01433456
 * extending either TextWebSocketHandler or BinaryWebSocketHandler
 */
public class MyHandler implements WebSocketHandler {

    /*
     *当前连接数
     */
    private static int onlineCount = 0;

    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();;



    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        Map<String,Object> attr = webSocketSession.getAttributes();
        for(Map.Entry<String,Object> entry:attr.entrySet()){
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            System.out.println("key=" + key + " value=" + value);
        }
        System.out.println("webSocketSession id=" + webSocketSession.getId());
        users.add(webSocketSession);
        //8192
        System.out.println("TextMessageSizeLimit=" + webSocketSession.getTextMessageSizeLimit());
        System.out.println("AcceptedProtocol=" + webSocketSession.getAcceptedProtocol());
        System.out.println("Connection established..."+webSocketSession.getRemoteAddress().toString());
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        try {
            System.out.println("Req: "+webSocketMessage.getPayload());
            TextMessage returnMessage = new TextMessage(webSocketMessage.getPayload()
                    + " received at server");
            webSocketSession.sendMessage(returnMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        if(webSocketSession.isOpen()){
            webSocketSession.close();
        }
        System.out.println(webSocketSession.toString());
        System.out.println("WS connection error,close...");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        System.out.println("Connection closed..."+webSocketSession.getRemoteAddress().toString());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
