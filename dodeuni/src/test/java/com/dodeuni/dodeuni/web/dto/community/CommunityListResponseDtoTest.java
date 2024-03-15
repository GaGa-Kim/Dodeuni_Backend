package com.dodeuni.dodeuni.web.dto.community;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.domain.community.CommunityTest;
import com.dodeuni.dodeuni.domain.community.Photo;
import com.dodeuni.dodeuni.domain.community.PhotoTest;
import com.dodeuni.dodeuni.domain.user.UserTest;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CommunityListResponseDtoTest {
    private Community community;

    public static CommunityListResponseDto testCommunityListResponseDto(Community community) {
        return new CommunityListResponseDto(community);
    }

    @BeforeEach
    void setUp() {
        community = CommunityTest.testCommunity();
        community.setUser(UserTest.testUser());
        community.setCreatedDateTime(LocalDateTime.now());
        Photo photo = PhotoTest.testPhoto();
        photo.setCommunity(community);
    }

    @Test
    @DisplayName("CommunityListResponseDto 생성 테스트")
    void testCommunityListResponseDtoSave() {
        CommunityListResponseDto communityListResponseDto = testCommunityListResponseDto(community);

        assertNotNull(communityListResponseDto);
        assertEquals(community.getId(), communityListResponseDto.getCommunityId());
        assertEquals(community.getUser().getId(), communityListResponseDto.getUserId());
        assertEquals(community.getUser().getEmail(), communityListResponseDto.getEmail());
        assertEquals(community.getUser().getNickname(), communityListResponseDto.getNickname());
        assertEquals(community.getMain().getMainName(), communityListResponseDto.getMain());
        assertEquals(community.getSub().getSubName(), communityListResponseDto.getSub());
        assertEquals(community.getTitle(), communityListResponseDto.getTitle());
        assertEquals(community.getContent(), communityListResponseDto.getContent());
        assertEquals(community.getPhotoList().get(0).getId(), communityListResponseDto.getThumbnailId());
        assertEquals(community.getPhotoList().get(0).getPhotoUrl(), communityListResponseDto.getThumbnailUrl());
        assertEquals(community.getCreatedDateTime(), communityListResponseDto.getCreatedDateTime());
    }
}