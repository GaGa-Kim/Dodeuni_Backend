package com.dodeuni.dodeuni.service.user;

import static com.dodeuni.dodeuni.domain.user.UserTest.USER_NEW_NICK_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserRepository;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.web.dto.user.UserResponseDto;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    private User user;

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
    }

    @Test
    @DisplayName("회원 프로필 상세 조회 테스트")
    void testGetUserProfile() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        UserResponseDto result = userService.getUserProfile(user.getId());

        assertNotNull(result);
        assertEquals(user.getId(), result.getUserId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getNickname(), result.getNickname());

        verify(userRepository).findById(anyLong());
    }

    @Test
    @DisplayName("회원 닉네임 수정 테스트")
    void testUpdateNickname() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        UserResponseDto result = userService.updateNickname(user.getId(), USER_NEW_NICK_NAME);

        assertNotNull(result);
        assertEquals(user.getId(), result.getUserId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(USER_NEW_NICK_NAME, result.getNickname());

        verify(userRepository).findById(anyLong());
    }

    @Test
    @DisplayName("회원 조회 테스트")
    void testFindByUserId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.ofNullable(user));

        User result = userService.findByUserId(user.getId());

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getNickname(), result.getNickname());
        assertNull(result.getFcmToken());
        assertEquals(user.getRole(), result.getRole());

        verify(userRepository).findById(anyLong());
    }
}