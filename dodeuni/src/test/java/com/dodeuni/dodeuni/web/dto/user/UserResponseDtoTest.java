package com.dodeuni.dodeuni.web.dto.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserResponseDtoTest {
    private User user;

    public static UserResponseDto testUserResponseDto(User user) {
        return new UserResponseDto(user);
    }

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
    }

    @Test
    @DisplayName("UserResponseDto 생성 테스트")
    void testUserResponseDtoSave() {
        UserResponseDto userResponseDto = testUserResponseDto(user);

        assertNotNull(userResponseDto);
        assertEquals(user.getId(), userResponseDto.getUserId());
        assertEquals(user.getEmail(), userResponseDto.getEmail());
        assertEquals(user.getNickname(), userResponseDto.getNickname());
    }
}