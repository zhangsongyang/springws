package com.example.springws.interceptor;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

/**
 * @author 01433456
 */
public class MyHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("Before handshake "+request.getRemoteAddress().toString());
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            String accessToken = servletRequest.getServletRequest().getParameter("accessToken");
            //accessToken权限校验，ok握手通过
            if(accessToken == null || "".equals(accessToken)){
                return false;
            }else{
                attributes.put("uid-token",accessToken);
            }
        }

        return super.beforeHandshake(request, response, wsHandler, attributes);

//        if (request instanceof ServletServerHttpRequest) {
//            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
//            HttpSession session = servletRequest.getServletRequest().getSession(false);
//            if (session != null) {
//                String userName = (String) session.getAttribute(Constants.SESSION_USERNAME);
//                if(userName == null){
//                    userName = "WEBSOCKET_USERNAME_IS_NULL";
//                }
//                attr.put(Constants.WEBSOCKET_USERNAME,userName);
//            }
//        }
//        return true;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
        System.out.println("After handshake "+request.getRemoteAddress().toString());
        super.afterHandshake(request, response, wsHandler, ex);
    }
}
