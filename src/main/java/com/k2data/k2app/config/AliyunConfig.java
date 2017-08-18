package com.k2data.k2app.config;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "aliyun")
public class AliyunConfig {

    private String accessKeyID;
    private String accessKeySecret;
    private String defaultMessageBody;
    private Sms sms;

    @Setter
    @Getter
    public static class Sms {
        private String topic;
        private String endPoint;

    }
}
