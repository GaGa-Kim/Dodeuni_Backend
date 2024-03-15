package com.dodeuni.dodeuni.service.auth;

import static com.dodeuni.dodeuni.web.dto.user.TokenResponseDtoTest.TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserRepository;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.jwt.JwtTokenProvider;
import com.dodeuni.dodeuni.web.dto.auth.UserSaveRequestDto;
import com.dodeuni.dodeuni.web.dto.auth.UserSaveRequestDtoTest;
import com.dodeuni.dodeuni.web.dto.user.TokenResponseDto;
import javax.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {
    private User user;

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
    }

    @Test
    @DisplayName("소셜 회원 가입 후 JWT 토큰과 회원 정보 조회 테스트")
    void testRegister() {
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtTokenProvider.generateToken(anyString())).thenReturn(TOKEN);
        doNothing().when(jwtTokenProvider).setHeaderAccessToken(any(), anyString());

        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDtoTest.testUserSaveRequestDto(user);
        TokenResponseDto result = authService.registerOrLogin(userSaveRequestDto, mock(HttpServletResponse.class));

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getNickname(), result.getNickname());
        assertEquals(TOKEN, result.getToken());

        verify(userRepository).existsByEmail(anyString());
        verify(userRepository).save(any(User.class));
        verify(jwtTokenProvider).generateToken(anyString());
        verify(jwtTokenProvider).setHeaderAccessToken(any(), anyString());
    }

    @Test
    @DisplayName("소셜 로그인 후 JWT 토큰과 회원 정보 조회 테스트")
    void testLogin() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        when(userRepository.findByEmail(anyString())).thenReturn(user);
        when(jwtTokenProvider.generateToken(anyString())).thenReturn(TOKEN);
        doNothing().when(jwtTokenProvider).setHeaderAccessToken(any(), anyString());

        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDtoTest.testUserSaveRequestDto(user);
        TokenResponseDto result = authService.registerOrLogin(userSaveRequestDto, mock(HttpServletResponse.class));

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getNickname(), result.getNickname());
        assertEquals(TOKEN, result.getToken());

        verify(userRepository).existsByEmail(anyString());
        verify(userRepository).findByEmail(anyString());
        verify(jwtTokenProvider).generateToken(anyString());
        verify(jwtTokenProvider).setHeaderAccessToken(any(), anyString());
    }
}