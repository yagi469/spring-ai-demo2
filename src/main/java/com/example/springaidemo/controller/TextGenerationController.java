package com.example.springaidemo.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/generate")
public class TextGenerationController {

    private final ChatClient chatClient;

    public TextGenerationController(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @PostMapping
    public String generate(@RequestBody String userPrompt) {
        Prompt prompt = new Prompt("Generate a helpful and concise response to: " + userPrompt);
        return chatClient.prompt(prompt).call().content();
    }
}
