package com.dodeuni.dodeuni.web.dto.comment;

import static com.dodeuni.dodeuni.web.dto.auth.UserSaveRequestDtoTest.INVALID_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.dodeuni.dodeuni.domain.comment.Comment;
import com.dodeuni.dodeuni.domain.comment.CommentTest;
import com.dodeuni.dodeuni.domain.community.CommunityTest;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.web.dto.ValidatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommentUpdateRequestDtoTest {
    private final ValidatorUtil<CommentUpdateRequestDto> validatorUtil = new ValidatorUtil<>();

    private Comment comment;

    public static CommentUpdateRequestDto testCommentUpdateRequestDto(Comment comment) {
        return CommentUpdateRequestDto.builder()
                .commentId(comment.getId())
                .communityId(comment.getCommunity().getId())
                .content(comment.getContent())
                .build();
    }

    @BeforeEach
    void setUp() {
        comment = CommentTest.testComment();
        comment.setUser(UserTest.testUser());
        comment.setCommunity(CommunityTest.testCommunity());
    }

    @Test
    @DisplayName("CommentUpdateRequestDto 생성 테스트")
    void testCommentUpdateRequestDtoSave() {
        CommentUpdateRequestDto commentUpdateRequestDto = testCommentUpdateRequestDto(comment);

        assertNotNull(commentUpdateRequestDto);
        assertEquals(comment.getId(), commentUpdateRequestDto.getCommentId());
        assertEquals(comment.getCommunity().getId(), commentUpdateRequestDto.getCommunityId());
        assertEquals(comment.getContent(), commentUpdateRequestDto.getContent());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        CommentUpdateRequestDto commentUpdateRequestDto = new CommentUpdateRequestDto();

        assertNotNull(commentUpdateRequestDto);
        assertNull(commentUpdateRequestDto.getCommentId());
        assertNull(commentUpdateRequestDto.getCommunityId());
        assertNull(commentUpdateRequestDto.getContent());
    }

    @Test
    @DisplayName("댓글 아이디 유효성 검증 테스트")
    void commentId_validation() {
        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .commentId(null)
                .communityId(comment.getCommunity().getId())
                .content(comment.getContent())
                .build();

        validatorUtil.validate(commentUpdateRequestDto);
    }

    @Test
    @DisplayName("커뮤니티 아이디 유효성 검증 테스트")
    void communityId_validation() {
        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .commentId(comment.getId())
                .communityId(null)
                .content(comment.getContent())
                .build();

        validatorUtil.validate(commentUpdateRequestDto);
    }

    @Test
    @DisplayName("댓글 내용 유효성 검증 테스트")
    void content_validation() {
        CommentUpdateRequestDto commentUpdateRequestDto = CommentUpdateRequestDto.builder()
                .commentId(comment.getId())
                .communityId(comment.getCommunity().getId())
                .content(INVALID_EMPTY)
                .build();

        validatorUtil.validate(commentUpdateRequestDto);
    }
}