package com.example.springaidemo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SummarizerService {

    private final ChatClient chatClient;

    public SummarizerService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String summarizeText(String content) {
        PromptTemplate promptTemplate = new PromptTemplate("Summarize this text in a concise way: {text}");
        Map<String, Object> variables = Map.of("text", content);
        Prompt prompt = promptTemplate.create(variables);
        return chatClient.prompt(prompt).call().content();
    }
}
