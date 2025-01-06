package com.example.forumservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${moderation.logging.enabled}")
    private boolean moderationLoggingEnabled;

    public boolean isModerationLoggingEnabled() {
        return moderationLoggingEnabled;
    }
}
