package com.bridgingcode.springbootactivemqdemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "json.ad")  // This matches the prefix in your application.properties
public class AdConfiguration {

    private String data;
    private String selection;
    private String queueName;

    // Getters and setters
    public String getJsonAdData() {
        return data;
    }

    public void setJsonAdData(String data) {
        this.data = data;
    }

    public String getJsonAdSelection() {
        return selection;
    }

    public void setJsonAdSelection(String selection) {
        this.selection = selection;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }
}
