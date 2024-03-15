package com.dodeuni.dodeuni.service.alarm;

import static com.dodeuni.dodeuni.domain.user.UserTest.USER_FCM_TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dodeuni.dodeuni.domain.comment.Comment;
import com.dodeuni.dodeuni.domain.comment.CommentTest;
import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.domain.community.CommunityTest;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.service.comment.CommentService;
import com.dodeuni.dodeuni.service.user.UserService;
import com.dodeuni.dodeuni.web.dto.alarm.AlarmResponseDto;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AlarmServiceImplTest {
    private User user;
    private Comment comment;

    @Mock
    private UserService userService;
    @Mock
    private CommentService commentService;
    @InjectMocks
    private AlarmServiceImpl alarmService;

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
        Community community = CommunityTest.testCommunity();
        community.setUser(user);
        comment = CommentTest.testComment();
        comment.setUser(user);
        comment.setCommunity(community);
    }

    @Test
    @DisplayName("회원의 기기-사용자 별 fcm 토큰 추가 테스트")
    void testUpdateFcmToken() {
        when(userService.findByUserId(anyLong())).thenReturn(user);

        alarmService.updateFcmToken(user.getId(), USER_FCM_TOKEN);

        verify(userService).findByUserId(anyLong());
    }

    @Test
    @DisplayName("회원이 작성한 커뮤니티에 달린 댓글에 대한 알람 목록 조회 테스트")
    void testGetCommentAlarmListByCommunity() {
        when(userService.findByUserId(anyLong())).thenReturn(user);
        when(commentService.getAllCommentList(anyList())).thenReturn(List.of(comment));

        List<AlarmResponseDto> result = alarmService.getCommentAlarmListByCommunity(user.getId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(comment.getCommunity().getId(), result.get(0).getCommunityId());

        verify(userService).findByUserId(anyLong());
        verify(commentService).getAllCommentList(anyList());
    }
}