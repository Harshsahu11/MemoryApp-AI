package com.detrox.advisiors_app.service;

import reactor.core.publisher.Flux;

public interface ChatService {
    public String chat(String query,String userId);

    Flux<String> streamChat(String query);
}
