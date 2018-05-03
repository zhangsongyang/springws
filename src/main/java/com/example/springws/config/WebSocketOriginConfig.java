package com.example.springws.config;

import com.example.springws.handler.MyHandler;
import com.example.springws.interceptor.MyHandshakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**WebSocket
 * @author 01433456
 */
@Configuration
@EnableScheduling
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketOriginConfig extends AbstractWebSocketMessageBrokerConfigurer implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        //注册websocket实现类，指定参数访问地址;allowed-origins="*" 允许跨域
        webSocketHandlerRegistry.addHandler(myHandler(), "/ws").addInterceptors(myHandshake()).setAllowedOrigins("*");
        //允许客户端使用SockJS
        webSocketHandlerRegistry.addHandler(myHandler(), "/sockjs/ws").addInterceptors(myHandshake()).withSockJS();
    }

    @Bean
    public MyHandler myHandler(){
        return new MyHandler();
    }

    @Bean
    public MyHandshakeInterceptor myHandshake(){
        return new MyHandshakeInterceptor();
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/sockjs/ws").withSockJS();
    }
}
