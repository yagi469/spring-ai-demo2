package com.example.springaidemo.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ChatService {

    private final ChatClient chatClient;
    private final Map<String, List<String>> chatHistory = new ConcurrentHashMap<>();

    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    public String handleMessage(String userId, String message) {
        // ユーザーの履歴を取得または新規作成
        List<String> history = chatHistory.computeIfAbsent(userId, k -> new ArrayList<>());
        // 新しいユーザーメッセージを履歴に追加
        history.add("User: " + message);

        // 履歴からコンテキスト文字列を構築
        String context = String.join("\n", history);
        // 新しいプロンプトを作成
        Prompt prompt = new Prompt(context + "\nAI:");

        // AIの返信を取得
        String aiReply = chatClient.prompt(prompt).call().content();
        // AIの返信を履歴に追加
        history.add("AI: " + aiReply);
        return aiReply;
    }
}
