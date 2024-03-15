package com.dodeuni.dodeuni.web.dto.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TokenResponseDtoTest {
    public static final String TOKEN = "token";
    private User user;

    public static TokenResponseDto testTokenResponseDto(User user) {
        return new TokenResponseDto(user, TOKEN);
    }

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
    }

    @Test
    @DisplayName("TokenResponseDto 생성 테스트")
    void testTokenResponseDtoSave() {
        TokenResponseDto tokenResponseDto = testTokenResponseDto(user);

        assertNotNull(tokenResponseDto);
        assertEquals(user.getId(), tokenResponseDto.getUserId());
        assertEquals(user.getEmail(), tokenResponseDto.getEmail());
        assertEquals(user.getNickname(), tokenResponseDto.getNickname());
        assertEquals(TOKEN, tokenResponseDto.getToken());
    }
}