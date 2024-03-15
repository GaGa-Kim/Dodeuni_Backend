package com.dodeuni.dodeuni.web;

import static com.dodeuni.dodeuni.domain.user.UserTest.USER_NEW_NICK_NAME;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dodeuni.dodeuni.config.SecurityConfig;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.jwt.JwtRequestFilter;
import com.dodeuni.dodeuni.service.user.UserService;
import com.dodeuni.dodeuni.web.dto.user.UserResponseDto;
import com.dodeuni.dodeuni.web.dto.user.UserResponseDtoTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = UserController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class UserControllerTest {
    private User user;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
    }

    @Test
    @DisplayName("회원 프로필 상세 조회 테스트")
    void testUserProfile() throws Exception {
        UserResponseDto userResponseDto = UserResponseDtoTest.testUserResponseDto(user);
        when(userService.getUserProfile(anyLong())).thenReturn(userResponseDto);

        mockMvc.perform(get("/api/user")
                        .param("userId", String.valueOf(user.getId()))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(userService).getUserProfile(anyLong());
    }

    @Test
    @DisplayName("회원 닉네임 수정 테스트")
    void testUpdateNickname() throws Exception {
        UserResponseDto userResponseDto = UserResponseDtoTest.testUserResponseDto(user);
        when(userService.updateNickname(anyLong(), anyString())).thenReturn(userResponseDto);

        mockMvc.perform(put("/api/user")
                        .param("userId", String.valueOf(user.getId()))
                        .param("newNickname", USER_NEW_NICK_NAME)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(userService).updateNickname(anyLong(), anyString());

    }
}