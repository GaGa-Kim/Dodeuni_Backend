package com.dodeuni.dodeuni.web.dto.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.web.dto.ValidatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserSaveRequestDtoTest {
    private final ValidatorUtil<UserSaveRequestDto> validatorUtil = new ValidatorUtil<>();

    public static final String INVALID_EMAIL = "dodeunigmail.com";
    public static final String INVALID_EMPTY = "";

    private User user;

    public static UserSaveRequestDto testUserSaveRequestDto(User user) {
        return UserSaveRequestDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
    }

    @Test
    @DisplayName("UserSaveRequestDto 생성 테스트")
    void testUserSaveRequestDtoSave() {
        UserSaveRequestDto userSaveRequestDto = testUserSaveRequestDto(user);

        assertNotNull(userSaveRequestDto);
        assertEquals(user.getEmail(), userSaveRequestDto.getEmail());
        assertEquals(user.getNickname(), userSaveRequestDto.getNickname());
    }

    @Test
    @DisplayName("UserSaveRequestDto toEntity 테스트")
    void testUserSaveRequestDtoToEntity() {
        UserSaveRequestDto userSaveRequestDto = testUserSaveRequestDto(user);

        User userEntity = userSaveRequestDto.toEntity();

        assertNotNull(userSaveRequestDto);
        assertEquals(user.getEmail(), userEntity.getEmail());
        assertEquals(user.getNickname(), userEntity.getNickname());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        UserSaveRequestDto userSaveRequestDto = new UserSaveRequestDto();

        assertNotNull(userSaveRequestDto);
        assertNull(userSaveRequestDto.getEmail());
        assertNull(userSaveRequestDto.getNickname());
    }

    @Test
    @DisplayName("회원 이메일 유효성 검증 테스트")
    void email_validation() {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .email(INVALID_EMAIL)
                .nickname(user.getNickname())
                .build();

        validatorUtil.validate(userSaveRequestDto);
    }

    @Test
    @DisplayName("회원 닉네임 유효성 검증 테스트")
    void nickname_validation() {
        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDto.builder()
                .email(user.getEmail())
                .nickname(INVALID_EMPTY)
                .build();

        validatorUtil.validate(userSaveRequestDto);
    }
}