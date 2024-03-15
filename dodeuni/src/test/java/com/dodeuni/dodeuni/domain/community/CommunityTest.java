package com.dodeuni.dodeuni.domain.community;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommunityTest {
    public static final Long COMMUNITY_ID = 1L;
    public static final String COMMUNITY_MAIN = Main.INFORMATION.getMainName();
    public static final String COMMUNITY_NEW_MAIN = Main.GATHERING.getMainName();
    public static final String COMMUNITY_SUB = Sub.EXCHANGE.getSubName();
    public static final String COMMUNITY_NEW_SUB = Sub.MARKET.getSubName();
    public static final String COMMUNITY_TITLE = "제목";
    public static final String COMMUNITY_NEW_TITLE = "새 제목";
    public static final String COMMUNITY_CONTENT = "내용";
    public static final String COMMUNITY_NEW_CONTENT = "새 내용";

    public static Community testCommunity() {
        return Community.builder()
                .id(COMMUNITY_ID)
                .main(COMMUNITY_MAIN)
                .sub(COMMUNITY_SUB)
                .title(COMMUNITY_TITLE)
                .content(COMMUNITY_CONTENT)
                .build();
    }

    private Community community;

    @BeforeEach
    void setUp() {
        community = testCommunity();
    }

    @Test
    @DisplayName("커뮤니티 게시글 추가 테스트")
    void testCommunitySave() {
        assertNotNull(community);
        assertEquals(COMMUNITY_ID, community.getId());
        assertEquals(COMMUNITY_MAIN, community.getMain().getMainName());
        assertEquals(COMMUNITY_SUB, community.getSub().getSubName());
        assertEquals(COMMUNITY_TITLE, community.getTitle());
        assertEquals(COMMUNITY_CONTENT, community.getContent());
    }

    @Test
    @DisplayName("커뮤니티 게시글 수정 테스트")
    void testUpdate() {
        community.update(COMMUNITY_NEW_MAIN, COMMUNITY_NEW_SUB, COMMUNITY_NEW_TITLE, COMMUNITY_NEW_CONTENT);

        assertEquals(COMMUNITY_NEW_MAIN, community.getMain().getMainName());
        assertEquals(COMMUNITY_NEW_SUB, community.getSub().getSubName());
        assertEquals(COMMUNITY_NEW_TITLE, community.getTitle());
        assertEquals(COMMUNITY_NEW_CONTENT, community.getContent());
    }
}