package com.example.forumservice.utils;

import com.example.forumservice.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigUtils {

    private static AppConfig appConfig;

    @Autowired
    public ConfigUtils(AppConfig appConfig) {
        ConfigUtils.appConfig = appConfig;
    }

    public static boolean isModerationLoggingEnabled() {
        return appConfig.isModerationLoggingEnabled();
    }
}
