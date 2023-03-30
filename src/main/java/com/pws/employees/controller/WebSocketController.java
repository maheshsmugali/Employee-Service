package com.pws.employees.controller;

import com.pws.employees.websocketclassess.Greeting;
import com.pws.employees.websocketclassess.HelloMessage;
import com.pws.employees.websocketmodel.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Controller
public class WebSocketController {

    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public ChatMessage register(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

}


//    @Bean
//    public WebSocketClient webSocketClient() {
//        return new StandardWebSocketClient();
//    }

//    public void connectToWebSocketServer() {
//        WebSocketClient webSocketClient = webSocketClient();
//        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
//
//        String url = "ws://localhost:8080/my-websocket-endpoint";
//        StompSessionHandler sessionHandler = new MyStompSessionHandler();
//
//        stompClient.connect(url, sessionHandler);
//    }
