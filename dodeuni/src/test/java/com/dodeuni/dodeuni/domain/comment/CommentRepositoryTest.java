package com.dodeuni.dodeuni.domain.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodeuni.dodeuni.domain.community.CommunityRepository;
import com.dodeuni.dodeuni.domain.community.CommunityTest;
import com.dodeuni.dodeuni.domain.user.UserRepository;
import com.dodeuni.dodeuni.domain.user.UserTest;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommentRepositoryTest {
    private Comment comment;

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommunityRepository communityRepository;

    @BeforeEach
    void setUp() {
        comment = CommentTest.testComment();
        comment.setUser(userRepository.save(UserTest.testUser()));
        comment.setCommunity(communityRepository.save(CommunityTest.testCommunity()));
        comment = commentRepository.save(comment);
    }

    @AfterEach
    void clean() {
        commentRepository.deleteAll();
        communityRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("오름차순 생성 시간에 따른 한 커뮤니티에 달린 댓글 목록 조회 테스트")
    void testFindAllByCommunityOrderByCreatedDateTimeAsc() {
        List<Comment> foundCommentList = commentRepository.findAllByCommunityOrderByCreatedDateTimeAsc(comment.getCommunity());

        assertNotNull(foundCommentList);
        assertEquals(1, foundCommentList.size());
    }

    @Test
    @DisplayName("내림차순 생성 시간에 따른 여러 커뮤니티에 달린 댓글 목록 조회 테스트")
    void testFindAllByCommunityInOrderByCreatedDateTimeDesc() {
        List<Comment> foundCommentList = commentRepository.findAllByCommunityInOrderByCreatedDateTimeDesc(List.of(comment.getCommunity()));

        assertNotNull(foundCommentList);
        assertEquals(1, foundCommentList.size());
    }
}