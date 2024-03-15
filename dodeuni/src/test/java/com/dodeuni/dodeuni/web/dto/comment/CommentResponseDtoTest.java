package com.dodeuni.dodeuni.web.dto.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodeuni.dodeuni.domain.comment.Comment;
import com.dodeuni.dodeuni.domain.comment.CommentTest;
import com.dodeuni.dodeuni.domain.community.CommunityTest;
import com.dodeuni.dodeuni.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommentResponseDtoTest {
    private Comment comment;

    public static CommentResponseDto testCommentResponseDto(Comment comment) {
        return new CommentResponseDto(comment);
    }

    @BeforeEach
    void setUp() {
        comment = CommentTest.testComment();
        comment.setUser(UserTest.testUser());
        comment.setCommunity(CommunityTest.testCommunity());
    }

    @Test
    @DisplayName("CommentResponseDto 생성 테스트")
    void testCommentResponseDtoSave() {
        CommentResponseDto commentResponseDto = testCommentResponseDto(comment);

        assertNotNull(commentResponseDto);
        assertEquals(comment.getId(), commentResponseDto.getCommentId());
        assertEquals(comment.getUser().getId(), commentResponseDto.getUserId());
        assertEquals(comment.getUser().getNickname(), commentResponseDto.getNickname());
        assertEquals(comment.getCommunity().getId(), commentResponseDto.getCommunityId());
        assertEquals(comment.getContent(), commentResponseDto.getContent());
        assertEquals(comment.getStep(), commentResponseDto.getStep());
        assertEquals(comment.getPid(), commentResponseDto.getPid());
        assertEquals(comment.getCreatedDateTime(), commentResponseDto.getCreatedDateTime());
        assertEquals(comment.getModifiedDateTime(), commentResponseDto.getModifiedDateTime());
    }
}