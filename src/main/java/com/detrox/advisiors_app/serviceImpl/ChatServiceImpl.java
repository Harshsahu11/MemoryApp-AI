package com.detrox.advisiors_app.serviceImpl;

import com.detrox.advisiors_app.service.ChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatServiceImpl implements ChatService {

    private ChatClient chatClient;

    public ChatServiceImpl(ChatClient chatClient){
        this.chatClient = chatClient;
    }

    @Value("classpath:prompts/userMessage.st")
    private Resource userMessage;

    @Value("classpath:prompts/systemMessage.st")
    private Resource systemMessage;

    @Override
    public String chat(String query) {


        return chatClient.prompt()
                .user(query)
                .advisors(advisor -> advisor
                        .param(ChatMemory.CONVERSATION_ID, "user-123"))
                .call()
                .content();

    }

    @Override
    public Flux<String> streamChat(String query) {
        return chatClient.prompt()
                .system(system-> system.text(this.systemMessage))
                .user(user->user.text(this.userMessage).param("concept",query))
                .stream()
                .content();
    }


}
