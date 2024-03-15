package com.dodeuni.dodeuni.domain.community;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
class CommunityRepositoryTest {
    private Community community;

    @Autowired
    private CommunityRepository communityRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        community = CommunityTest.testCommunity();
        community.setUser(userRepository.save(UserTest.testUser()));
        community = communityRepository.save(community);
    }

    @AfterEach
    void clean() {
        communityRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("카테고리 대분류와 소분류로 커뮤니티 게시글 조회 테스트")
    void testFindByMainAndSub() {
        List<Community> foundCommunityList = communityRepository.findByMainAndSub(community.getMain(), community.getSub());

        assertNotNull(foundCommunityList);
        assertEquals(community.getMain(), foundCommunityList.get(0).getMain());
        assertEquals(community.getSub(), foundCommunityList.get(0).getSub());
    }
}