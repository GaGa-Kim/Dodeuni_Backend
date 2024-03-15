package com.dodeuni.dodeuni.web;

import static com.dodeuni.dodeuni.domain.user.UserTest.USER_FCM_TOKEN;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dodeuni.dodeuni.config.SecurityConfig;
import com.dodeuni.dodeuni.domain.comment.Comment;
import com.dodeuni.dodeuni.domain.comment.CommentTest;
import com.dodeuni.dodeuni.domain.community.CommunityTest;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.jwt.JwtRequestFilter;
import com.dodeuni.dodeuni.service.alarm.AlarmService;
import com.dodeuni.dodeuni.web.dto.alarm.AlarmResponseDto;
import com.dodeuni.dodeuni.web.dto.alarm.AlarmResponseDtoTest;
import java.util.List;
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

@WebMvcTest(controllers = AlarmController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class AlarmControllerTest {
    private User user;
    private Comment comment;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AlarmService alarmService;

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
        comment = CommentTest.testComment();
        comment.setUser(user);
        comment.setCommunity(CommunityTest.testCommunity());
    }

    @Test
    @DisplayName("회원의 기기-사용자 별 fcm 토큰 추가 테스트")
    void testUpdateFcmToken() throws Exception {
        doNothing().when(alarmService).updateFcmToken(anyLong(), anyString());

        mockMvc.perform(post("/api/alarm")
                        .param("userId", String.valueOf(user.getId()))
                        .param("fcmToken", USER_FCM_TOKEN)
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());

        verify(alarmService).updateFcmToken(anyLong(), anyString());
    }

    @Test
    @DisplayName("회원이 작성한 커뮤니티에 달린 댓글에 대한 알람 목록 조회 테스트")
    void testCommentAlarmList() throws Exception {
        List<AlarmResponseDto> alarmResponseDtoList = List.of(AlarmResponseDtoTest.testAlarmResponseDto(comment));
        when(alarmService.getCommentAlarmListByCommunity(anyLong())).thenReturn(alarmResponseDtoList);

        mockMvc.perform(get("/api/alarm")
                        .param("userId", String.valueOf(user.getId()))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(alarmService).getCommentAlarmListByCommunity(anyLong());
    }
}