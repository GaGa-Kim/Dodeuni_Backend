package com.dodeuni.dodeuni.service;

import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FcmService {

    @Value("${firebase.icon}")
    String icon;

    public void sendMessage(String registrationToken, String body, Long communityId) throws FirebaseMessagingException {

        Message message = Message.builder()
                                .setAndroidConfig(AndroidConfig.builder()
                                        .setTtl(3000*1000)
                                        .setPriority(AndroidConfig.Priority.HIGH)
                                        .setRestrictedPackageName("dodeunifront.dodeuni")
                                        .setDirectBootOk(true)
                                        .setNotification(AndroidNotification.builder()
                                                .setTitle("도드니")
                                                .setBody(body)
                                                .setIcon(icon)
                                                .build())
                                        .build())
                .putData("communityId", communityId.toString())
                .setToken(registrationToken)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("FCM_Send: " + response);
        } catch (FirebaseMessagingException e) {
            log.info("FCM_Except: "+e.getMessage());
        }
    }
}
