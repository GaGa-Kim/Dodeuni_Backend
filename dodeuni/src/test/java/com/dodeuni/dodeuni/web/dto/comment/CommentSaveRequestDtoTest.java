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

public class CommentSaveRequestDtoTest {
    private final ValidatorUtil<CommentSaveRequestDto> validatorUtil = new ValidatorUtil<>();

    private Comment comment;

    public static CommentSaveRequestDto testCommentSaveRequestDto(Comment comment) {
        return CommentSaveRequestDto.builder()
                .userId(comment.getUser().getId())
                .communityId(comment.getCommunity().getId())
                .content(comment.getContent())
                .step(comment.getStep())
                .pid(comment.getPid())
                .build();
    }

    @BeforeEach
    void setUp() {
        comment = CommentTest.testComment();
        comment.setUser(UserTest.testUser());
        comment.setCommunity(CommunityTest.testCommunity());
    }

    @Test
    @DisplayName("CommentSaveRequestDto 생성 테스트")
    void testCommentSaveRequestDtoSave() {
        CommentSaveRequestDto commentSaveRequestDto = testCommentSaveRequestDto(comment);

        assertNotNull(commentSaveRequestDto);
        assertEquals(comment.getUser().getId(), commentSaveRequestDto.getUserId());
        assertEquals(comment.getCommunity().getId(), commentSaveRequestDto.getCommunityId());
        assertEquals(comment.getContent(), commentSaveRequestDto.getContent());
        assertEquals(comment.getStep(), commentSaveRequestDto.getStep());
        assertEquals(comment.getPid(), commentSaveRequestDto.getPid());
    }

    @Test
    @DisplayName("CommentSaveRequestDto toEntity 테스트")
    void testCommentSaveRequestDtoToEntity() {
        CommentSaveRequestDto commentSaveRequestDto = testCommentSaveRequestDto(comment);

        Comment commentEntity = commentSaveRequestDto.toEntity();
        commentEntity.setUser(comment.getUser());
        commentEntity.setCommunity(comment.getCommunity());

        assertNotNull(commentSaveRequestDto);
        assertEquals(commentSaveRequestDto.getUserId(), commentEntity.getUser().getId());
        assertEquals(commentSaveRequestDto.getCommunityId(), commentEntity.getCommunity().getId());
        assertEquals(commentSaveRequestDto.getContent(), commentEntity.getContent());
        assertEquals(commentSaveRequestDto.getStep(), commentEntity.getStep());
        assertEquals(commentSaveRequestDto.getPid(), commentEntity.getPid());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        CommentSaveRequestDto commentSaveRequestDto = new CommentSaveRequestDto();

        assertNotNull(commentSaveRequestDto);
        assertNull(commentSaveRequestDto.getUserId());
        assertNull(commentSaveRequestDto.getCommunityId());
        assertNull(commentSaveRequestDto.getContent());
        assertNull(commentSaveRequestDto.getStep());
        assertNull(commentSaveRequestDto.getPid());
    }

    @Test
    @DisplayName("회원 아이디 유효성 검증 테스트")
    void userId_validation() {
        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .userId(null)
                .communityId(comment.getCommunity().getId())
                .content(comment.getContent())
                .step(comment.getStep())
                .pid(comment.getPid())
                .build();

        validatorUtil.validate(commentSaveRequestDto);
    }

    @Test
    @DisplayName("커뮤니티 아이디 유효성 검증 테스트")
    void communityId_validation() {
        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .userId(comment.getUser().getId())
                .communityId(null)
                .content(comment.getContent())
                .step(comment.getStep())
                .pid(comment.getPid())
                .build();

        validatorUtil.validate(commentSaveRequestDto);
    }

    @Test
    @DisplayName("댓글 내용 유효성 검증 테스트")
    void content_validation() {
        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .userId(comment.getUser().getId())
                .communityId(comment.getCommunity().getId())
                .content(INVALID_EMPTY)
                .step(comment.getStep())
                .pid(comment.getPid())
                .build();

        validatorUtil.validate(commentSaveRequestDto);
    }

    @Test
    @DisplayName("댓글 계층 유효성 검증 테스트")
    void step_validation() {
        CommentSaveRequestDto commentSaveRequestDto = CommentSaveRequestDto.builder()
                .userId(comment.getUser().getId())
                .communityId(comment.getCommunity().getId())
                .content(comment.getContent())
                .step(null)
                .pid(comment.getPid())
                .build();

        validatorUtil.validate(commentSaveRequestDto);
    }
}