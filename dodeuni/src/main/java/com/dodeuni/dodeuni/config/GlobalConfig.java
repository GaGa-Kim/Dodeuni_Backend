package com.dodeuni.dodeuni.config;

import java.util.Objects;
import javax.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@Getter
public class GlobalConfig {
    @Autowired
    private Environment environment;

    private String jwtSecret;
    private int expirationTime;

    private String accessKey;
    private String secretKey;
    private String region;
    private String bucket;

    private String fckKeyPath;
    private String icon;

    @PostConstruct
    public void init() {
        initJWT();
        initCloud();
        initAlarm();
    }

    private void initJWT() {
        jwtSecret = environment.getProperty("jwt.secret");
        expirationTime = Integer.parseInt(Objects.requireNonNull(environment.getProperty("jwt.expiration_time")));
    }

    private void initCloud() {
        accessKey = environment.getProperty("cloud.aws.credentials.access-key");
        secretKey = environment.getProperty("cloud.aws.credentials.secret-key");
        region = environment.getProperty("cloud.aws.region.static");
        bucket = environment.getProperty("cloud.aws.s3.bucket");
    }

    private void initAlarm() {
        fckKeyPath = environment.getProperty("firebase.key-path");
        icon = environment.getProperty("firebase.icon");
    }
}