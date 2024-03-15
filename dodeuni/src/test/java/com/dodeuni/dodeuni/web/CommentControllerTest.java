package com.dodeuni.dodeuni.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dodeuni.dodeuni.config.SecurityConfig;
import com.dodeuni.dodeuni.domain.comment.Comment;
import com.dodeuni.dodeuni.domain.comment.CommentTest;
import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.domain.community.CommunityTest;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.jwt.JwtRequestFilter;
import com.dodeuni.dodeuni.service.comment.CommentService;
import com.dodeuni.dodeuni.web.dto.comment.CommentResponseDto;
import com.dodeuni.dodeuni.web.dto.comment.CommentResponseDtoTest;
import com.dodeuni.dodeuni.web.dto.comment.CommentSaveRequestDto;
import com.dodeuni.dodeuni.web.dto.comment.CommentSaveRequestDtoTest;
import com.dodeuni.dodeuni.web.dto.comment.CommentUpdateRequestDto;
import com.dodeuni.dodeuni.web.dto.comment.CommentUpdateRequestDtoTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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

@WebMvcTest(controllers = CommentController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class CommentControllerTest {
    private Comment comment;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommentService commentService;

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
    void testSave() throws Exception {
        List<CommentResponseDto> commentResponseDtoList = List.of(CommentResponseDtoTest.testCommentResponseDto(comment));
        when(commentService.saveComment(any())).thenReturn(commentResponseDtoList);

        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDtoTest.testCommentSaveRequestDto(comment);
        mockMvc.perform(post("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentSaveRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(commentService).saveComment(any());
    }

    @Test
    @DisplayName("커뮤니티 게시글 아이디에 따른 댓글 목록 조회 테스트")
    void testList() throws Exception {
        List<CommentResponseDto> commentResponseDtoList = List.of(CommentResponseDtoTest.testCommentResponseDto(comment));
        when(commentService.getCommentList(anyLong())).thenReturn(commentResponseDtoList);

        mockMvc.perform(get("/api/comments/list")
                        .param("communityId", String.valueOf(comment.getCommunity().getId()))
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());

        verify(commentService).getCommentList(anyLong());
    }

    @Test
    @DisplayName("커뮤니티 게시글의 댓글 수정 테스트")
    void testUpdate() throws Exception {
        List<CommentResponseDto> commentResponseDtoList = List.of(CommentResponseDtoTest.testCommentResponseDto(comment));
        when(commentService.updateComment(any())).thenReturn(commentResponseDtoList);

        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDtoTest.testCommentUpdateRequestDto(comment);
        mockMvc.perform(put("/api/comments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(commentUpdateRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(commentService).updateComment(any());
    }

    @Test
    @DisplayName("커뮤니티 게시글의 댓글 삭제 테스트")
    void testDelete() throws Exception {
        List<CommentResponseDto> commentResponseDtoList = List.of(CommentResponseDtoTest.testCommentResponseDto(comment));
        when(commentService.deleteComment(anyLong(), anyLong())).thenReturn(commentResponseDtoList);

        mockMvc.perform(delete("/api/comments")
                        .param("commentId", String.valueOf(comment.getId()))
                        .param("communityId", String.valueOf(comment.getCommunity().getId()))
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());

        verify(commentService).deleteComment(anyLong(), anyLong());
    }
}