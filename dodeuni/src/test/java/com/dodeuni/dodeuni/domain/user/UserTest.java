package com.dodeuni.dodeuni.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {
    public static final Long USER_ID = 1L;
    public static final String USER_EMAIL = "dodeuni@gmail.com";
    public static final String USER_NICK_NAME = "도드니";
    public static final String USER_NEW_NICK_NAME = "도드닝";
    public static final String USER_FCM_TOKEN = "A12D34";

    public static User testUser() {
        return User.builder()
                .id(USER_ID)
                .email(USER_EMAIL)
                .nickname(USER_NICK_NAME)
                .build();
    }

    private User user;

    @BeforeEach
    void setUp() {
        user = testUser();
    }

    @Test
    @DisplayName("회원 추가 테스트")
    void testUserSave() {
        assertNotNull(user);
        assertEquals(USER_ID, user.getId());
        assertEquals(USER_EMAIL, user.getEmail());
        assertEquals(USER_NICK_NAME, user.getNickname());
        assertNull(user.getFcmToken());
        assertEquals(Role.USER, user.getRole());
    }


    @Test
    @DisplayName("회원 프로필 수정 테스트")
    void testUpdateProfile() {
        user.updateProfile(USER_NEW_NICK_NAME);

        assertEquals(USER_NEW_NICK_NAME, user.getNickname());
    }

    @Test
    @DisplayName("회원 fcm 토큰 추가 테스트")
    void testUpdateFcmToken() {
        user.updateFcmToken(USER_FCM_TOKEN);

        assertEquals(USER_FCM_TOKEN, user.getFcmToken());
    }
}