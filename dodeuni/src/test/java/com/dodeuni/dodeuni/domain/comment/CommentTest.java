package com.dodeuni.dodeuni.domain.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommentTest {
    public static final Long COMMENT_ID = 1L;
    public static final String COMMENT_CONTENT = "내용";
    public static final String COMMENT_NEW_CONTENT = "새 내용";
    public static final Long COMMENT_STEP = 0L;
    public static final Long COMMENT_PID = null;

    public static Comment testComment() {
        return Comment.builder()
                .id(COMMENT_ID)
                .content(COMMENT_CONTENT)
                .step(COMMENT_STEP)
                .pid(COMMENT_PID)
                .build();
    }

    private Comment comment;

    @BeforeEach
    void setUp() {
        comment = testComment();
    }

    @Test
    @DisplayName("댓글 추가 테스트")
    void testCommentSave() {
        assertNotNull(comment);
        assertEquals(COMMENT_ID, comment.getId());
        assertEquals(COMMENT_CONTENT, comment.getContent());
        assertEquals(COMMENT_STEP, comment.getStep());
        assertEquals(COMMENT_PID, comment.getPid());
    }

    @Test
    @DisplayName("댓글의 작성자 회원 연관관계 설정 테스트")
    void testSetUser() {
        User user = mock(User.class);
        comment.setUser(user);

        assertEquals(user, comment.getUser());
    }

    @Test
    @DisplayName("댓글의 커뮤니티 연관관계 설정 테스트")
    void tsetSetCommunity() {
        Community community = mock(Community.class);
        comment.setCommunity(community);

        assertEquals(community, comment.getCommunity());
    }
}