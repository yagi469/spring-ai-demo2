package com.example.springaidemo.controller;

import com.example.springaidemo.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatClient chatClient;
    private final ChatService chatService;

    public ChatController(ChatClient.Builder builder, ChatService chatService) {
        this.chatClient = builder.build();
        this.chatService = chatService;
    }

    @GetMapping("/")
    public ChatResponse chat() {
        return chatClient.prompt()
                .user("Spring AIについて教えて")
                .call()
                .chatResponse();
    }

    @PostMapping("/{userId}")
    public String sendMessage(@PathVariable String userId, @RequestBody String message) {
        return chatService.handleMessage(userId, message);
    }

}
