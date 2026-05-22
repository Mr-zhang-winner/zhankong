package com.ruoyi.xkd.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * WebSocket 大一统核心配置类 (完全切换为高性能 STOMP 协议)
 */
@Configuration
@EnableWebSocketMessageBroker // 开启 STOMP 消息代理，自动注入 SimpMessagingTemplate
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册统一的握手端点
        // 前端通过网关访问 ws://网关IP:端口/ws-stomp 时，网关裁剪后正好匹配服务内的 /ws-stomp
        registry.addEndpoint("/ws-stomp")
                .setAllowedOriginPatterns("*")  // 允许所有来源（生产环境应配置具体域名）
                .withSockJS();  // 开启 SockJS 回退机制，增强信创系统浏览器的兼容性
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 定义广播频道的前缀。/topic 用于无脑广播，/user 用于点对点单发
        registry.enableSimpleBroker("/topic", "/user");

        // 客户端如果想主动往后端发送消息时的前缀
        registry.setApplicationDestinationPrefixes("/app");
        
        // 用户目的地前缀（用于点对点推送）
        registry.setUserDestinationPrefix("/user");
    }
}