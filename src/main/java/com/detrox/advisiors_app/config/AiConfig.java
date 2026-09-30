package com.detrox.advisiors_app.config;


import com.detrox.advisiors_app.advisors.TokenPrintAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AiConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory){

        MessageChatMemoryAdvisor messageChatMemoryAdvisor = MessageChatMemoryAdvisor
                .builder(chatMemory).build();


        return builder
                .defaultAdvisors(
                        messageChatMemoryAdvisor,
                        new TokenPrintAdvisor(),
                        new SimpleLoggerAdvisor(),
                        new SafeGuardAdvisor(List.of("games"))
                )
                .defaultSystem("You are a helpful coding assistant. You are an expert in coding.")
                .build();
    }
}
