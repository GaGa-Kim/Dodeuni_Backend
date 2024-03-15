package com.dodeuni.dodeuni.service.alarm;

import com.dodeuni.dodeuni.config.GlobalConfig;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.AndroidConfig;
import com.google.firebase.messaging.AndroidNotification;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import java.io.IOException;
import java.io.InputStream;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FcmSender {
    private final GlobalConfig config;

    @PostConstruct
    public void init() {
        try {
            InputStream refreshToken = new ClassPathResource(config.getFckKeyPath()).getInputStream();
            FirebaseOptions options = FirebaseOptions.builder().setCredentials(GoogleCredentials.fromStream(refreshToken)).build();
            FirebaseApp.initializeApp(options);
            log.info("Fcm Setting Completed");
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void sendMessage(String registrationToken, String body, Long communityId) {
        Message message = createAlarmMessage(registrationToken, body, communityId);
        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("FCM_Send: " + response);
        } catch (FirebaseMessagingException e) {
            log.info("FCM_Except: " + e.getMessage());
        }
    }

    private Message createAlarmMessage(String registrationToken, String body, Long communityId) {
        return Message.builder()
                .setAndroidConfig(AndroidConfig.builder()
                        .setTtl(3000 * 1000)
                        .setPriority(AndroidConfig.Priority.HIGH)
                        .setRestrictedPackageName("dodeunifront.dodeuni")
                        .setDirectBootOk(true)
                        .setNotification(AndroidNotification.builder()
                                .setTitle("도드니")
                                .setBody(body)
                                .setIcon(config.getIcon())
                                .build())
                        .build())
                .putData("communityId", communityId.toString())
                .setToken(registrationToken)
                .build();
    }
}