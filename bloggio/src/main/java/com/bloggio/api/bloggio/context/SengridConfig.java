package com.bloggio.api.bloggio.context;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SengridConfig {

    @Value("${sendgrid.api-key}")
    private String sendGridApiKey;

    public String getSendGridApiKey() {
        return sendGridApiKey;
    }

}
