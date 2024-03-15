package com.dodeuni.dodeuni.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dodeuni.dodeuni.config.SecurityConfig;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.jwt.JwtRequestFilter;
import com.dodeuni.dodeuni.service.auth.AuthService;
import com.dodeuni.dodeuni.web.dto.auth.TokenResponseDto;
import com.dodeuni.dodeuni.web.dto.auth.TokenResponseDtoTest;
import com.dodeuni.dodeuni.web.dto.auth.UserSaveRequestDto;
import com.dodeuni.dodeuni.web.dto.auth.UserSaveRequestDtoTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = AuthController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class AuthControllerTest {
    private User user;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthService authService;

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
    }

    @Test
    @DisplayName("소셜 회원 가입 또는 로그인을 진행한 후 JWT 토큰과 회원 정보를 발급")
    void testRegisterOrLogin() throws Exception {
        TokenResponseDto tokenResponseDto = TokenResponseDtoTest.testTokenResponseDto(user);
        when(authService.registerOrLogin(any(), any())).thenReturn(tokenResponseDto);

        UserSaveRequestDto userSaveRequestDto = UserSaveRequestDtoTest.testUserSaveRequestDto(user);
        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userSaveRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(authService).registerOrLogin(any(), any());
    }
}