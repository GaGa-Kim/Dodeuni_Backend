package com.dodeuni.dodeuni.service.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dodeuni.dodeuni.domain.comment.Comment;
import com.dodeuni.dodeuni.domain.comment.CommentRepository;
import com.dodeuni.dodeuni.domain.comment.CommentTest;
import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.domain.community.CommunityTest;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.service.alarm.FcmSender;
import com.dodeuni.dodeuni.service.community.CommunityService;
import com.dodeuni.dodeuni.service.user.UserService;
import com.dodeuni.dodeuni.web.dto.comment.CommentResponseDto;
import com.dodeuni.dodeuni.web.dto.comment.CommentSaveRequestDto;
import com.dodeuni.dodeuni.web.dto.comment.CommentSaveRequestDtoTest;
import com.dodeuni.dodeuni.web.dto.comment.CommentUpdateRequestDto;
import com.dodeuni.dodeuni.web.dto.comment.CommentUpdateRequestDtoTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    private Comment comment;

    @Mock
    private CommentRepository commentRepository;
    @Mock
    private UserService userService;
    @Mock
    private CommunityService communityService;
    @Mock
    private FcmSender fcmSender;
    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        comment = CommentTest.testComment();
        User user = UserTest.testUser();
        Community community = CommunityTest.testCommunity();
        community.setUser(user);
        comment.setUser(user);
        comment.setCommunity(community);
    }

    @Test
    @DisplayName("커뮤니티 게시글의 댓글 저장 테스트")
    void testSaveComment() {
        when(communityService.findByCommunityId(anyLong())).thenReturn(comment.getCommunity());
        when(userService.findByUserId(anyLong())).thenReturn(comment.getUser());
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);
        doNothing().when(fcmSender).sendMessage(any(), anyString(), anyLong());
        when(commentRepository.findAllByCommunityOrderByCreatedDateTimeAsc(any())).thenReturn(List.of(comment));

        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDtoTest.testCommentSaveRequestDto(comment);
        List<CommentResponseDto> result = commentService.saveComment(commentSaveRequestDto);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(comment.getUser().getId(), result.get(0).getUserId());
        assertEquals(comment.getUser().getNickname(), result.get(0).getNickname());
        assertEquals(comment.getCommunity().getId(), result.get(0).getCommunityId());
        assertEquals(comment.getContent(), result.get(0).getContent());
        assertEquals(comment.getStep(), result.get(0).getStep());
        assertEquals(comment.getPid(), result.get(0).getPid());
        assertEquals(comment.getCreatedDateTime(), result.get(0).getCreatedDateTime());
        assertEquals(comment.getModifiedDateTime(), result.get(0).getModifiedDateTime());

        verify(communityService).findByCommunityId(anyLong());
        verify(userService).findByUserId(anyLong());
        verify(commentRepository).save(any(Comment.class));
        verify(fcmSender).sendMessage(any(), anyString(), anyLong());
        verify(commentRepository).findAllByCommunityOrderByCreatedDateTimeAsc(any());
    }

    @Test
    @DisplayName("커뮤니티 게시글 아이디에 따른 댓글 목록 조회 테스트")
    void testGetCommentList() {
        when(communityService.findByCommunityId(anyLong())).thenReturn(comment.getCommunity());
        when(commentRepository.findAllByCommunityOrderByCreatedDateTimeAsc(any())).thenReturn(List.of(comment));

        List<CommentResponseDto> result = commentService.getCommentList(comment.getCommunity().getId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(comment.getUser().getId(), result.get(0).getUserId());
        assertEquals(comment.getUser().getNickname(), result.get(0).getNickname());
        assertEquals(comment.getCommunity().getId(), result.get(0).getCommunityId());
        assertEquals(comment.getContent(), result.get(0).getContent());
        assertEquals(comment.getStep(), result.get(0).getStep());
        assertEquals(comment.getPid(), result.get(0).getPid());
        assertEquals(comment.getCreatedDateTime(), result.get(0).getCreatedDateTime());
        assertEquals(comment.getModifiedDateTime(), result.get(0).getModifiedDateTime());

        verify(communityService).findByCommunityId(anyLong());
        verify(commentRepository).findAllByCommunityOrderByCreatedDateTimeAsc(any());
    }

    @Test
    @DisplayName("회원이 작성한 커뮤니티 게시글 목록에 달린 댓글 목록에 대해 내림차순으로 정렬 조회 테스트")
    void testGetAllCommentList() {
        when(commentRepository.findAllByCommunityInOrderByCreatedDateTimeDesc(anyList())).thenReturn(List.of(comment));

        List<Comment> result = commentService.getAllCommentList(List.of(comment.getCommunity()));

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(comment.getId(), result.get(0).getCommunity().getId());
        assertEquals(comment.getUser(), result.get(0).getUser());
        assertEquals(comment.getCommunity(), result.get(0).getCommunity());
        assertEquals(comment.getContent(), result.get(0).getContent());
        assertEquals(comment.getStep(), result.get(0).getStep());
        assertEquals(comment.getPid(), result.get(0).getPid());

        verify(commentRepository).findAllByCommunityInOrderByCreatedDateTimeDesc(anyList());
    }

    @Test
    @DisplayName("커뮤니티 게시글의 댓글 수정 테스트")
    void testUpdateComment() {
        when(commentRepository.findById(anyLong())).thenReturn(Optional.ofNullable(comment));
        when(communityService.findByCommunityId(anyLong())).thenReturn(comment.getCommunity());
        when(commentRepository.findAllByCommunityOrderByCreatedDateTimeAsc(any())).thenReturn(List.of(comment));

        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDtoTest.testCommentUpdateRequestDto(comment);
        List<CommentResponseDto> result = commentService.updateComment(commentUpdateRequestDto);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(comment.getUser().getId(), result.get(0).getUserId());
        assertEquals(comment.getUser().getNickname(), result.get(0).getNickname());
        assertEquals(comment.getCommunity().getId(), result.get(0).getCommunityId());
        assertEquals(comment.getContent(), result.get(0).getContent());
        assertEquals(comment.getStep(), result.get(0).getStep());
        assertEquals(comment.getPid(), result.get(0).getPid());
        assertEquals(comment.getCreatedDateTime(), result.get(0).getCreatedDateTime());
        assertEquals(comment.getModifiedDateTime(), result.get(0).getModifiedDateTime());

        verify(commentRepository).findById(anyLong());
        verify(communityService).findByCommunityId(anyLong());
        verify(commentRepository).findAllByCommunityOrderByCreatedDateTimeAsc(any());
    }

    @Test
    @DisplayName("커뮤니티 게시글의 댓글 삭제 테스트")
    void testDeleteComment() {
        doNothing().when(commentRepository).deleteById(anyLong());
        when(commentRepository.findAllByCommunityOrderByCreatedDateTimeAsc(any())).thenReturn(List.of(comment));

        List<CommentResponseDto> result = commentService.deleteComment(comment.getId(), comment.getCommunity().getId());

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(comment.getUser().getId(), result.get(0).getUserId());
        assertEquals(comment.getUser().getNickname(), result.get(0).getNickname());
        assertEquals(comment.getCommunity().getId(), result.get(0).getCommunityId());
        assertEquals(comment.getContent(), result.get(0).getContent());
        assertEquals(comment.getStep(), result.get(0).getStep());
        assertEquals(comment.getPid(), result.get(0).getPid());
        assertEquals(comment.getCreatedDateTime(), result.get(0).getCreatedDateTime());
        assertEquals(comment.getModifiedDateTime(), result.get(0).getModifiedDateTime());

        verify(commentRepository).deleteById(anyLong());
        verify(commentRepository).findAllByCommunityOrderByCreatedDateTimeAsc(any());
    }
}