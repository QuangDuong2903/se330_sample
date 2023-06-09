package com.quangduong.SE330backend.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Date;

@Controller
public class ChatController {
    @MessageMapping("/message/{id}")
    @SendTo("/chatroom/{id}")
    public Message sendMessage(@Payload Message message) {
        return message;
    }

    record Message(long boardId, String boardName, String email, String displayName, String photoUrl, String message, Date timestamp) { }

}
