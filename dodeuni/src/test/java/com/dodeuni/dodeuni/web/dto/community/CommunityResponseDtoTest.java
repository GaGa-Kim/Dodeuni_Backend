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

class CommunityResponseDtoTest {
    private Community community;

    public static CommunityResponseDto testCommunityResponseDto(Community community) {
        return new CommunityResponseDto(community);
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
    @DisplayName("CommunityResponseDto 생성 테스트")
    void testCommunityResponseDtoDtoSave() {
        CommunityResponseDto communityResponseDto = testCommunityResponseDto(community);

        assertNotNull(communityResponseDto);
        assertEquals(community.getId(), communityResponseDto.getCommunityId());
        assertEquals(community.getUser().getId(), communityResponseDto.getUserId());
        assertEquals(community.getUser().getEmail(), communityResponseDto.getEmail());
        assertEquals(community.getUser().getNickname(), communityResponseDto.getNickname());
        assertEquals(community.getMain().getMainName(), communityResponseDto.getMain());
        assertEquals(community.getSub().getSubName(), communityResponseDto.getSub());
        assertEquals(community.getTitle(), communityResponseDto.getTitle());
        assertEquals(community.getContent(), communityResponseDto.getContent());
        assertEquals(community.getPhotoList().get(0).getId(), communityResponseDto.getPhotoId().get(0));
        assertEquals(community.getPhotoList().get(0).getPhotoUrl(), communityResponseDto.getPhotoUrl().get(0));
        assertEquals(community.getCreatedDateTime(), communityResponseDto.getCreatedDateTime());
    }
}