package com.dodeuni.dodeuni.service.community;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.domain.community.CommunityRepository;
import com.dodeuni.dodeuni.domain.community.CommunityTest;
import com.dodeuni.dodeuni.domain.community.Photo;
import com.dodeuni.dodeuni.domain.community.PhotoRepository;
import com.dodeuni.dodeuni.domain.community.PhotoTest;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.service.user.UserService;
import com.dodeuni.dodeuni.web.dto.community.CommunityListResponseDto;
import com.dodeuni.dodeuni.web.dto.community.CommunityResponseDto;
import com.dodeuni.dodeuni.web.dto.community.CommunitySaveRequestDto;
import com.dodeuni.dodeuni.web.dto.community.CommunitySaveRequestDtoTest;
import com.dodeuni.dodeuni.web.dto.community.CommunityUpdateRequestDto;
import com.dodeuni.dodeuni.web.dto.community.CommunityUpdateRequestDtoTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommunityServiceImplTest {
    private Community community;

    @Mock
    private CommunityRepository communityRepository;
    @Mock
    private PhotoRepository photoRepository;
    @Mock
    private UserService userService;
    @Mock
    private PhotoService photoService;
    @InjectMocks
    private CommunityServiceImpl communityService;

    @BeforeEach
    void setUp() {
        community = CommunityTest.testCommunity();
        community.setUser(UserTest.testUser());
        Photo photo = PhotoTest.testPhoto();
        photo.setCommunity(community);
    }

    @Test
    @DisplayName("커뮤니티 게시글 저장 테스트")
    void testSaveCommunity() {
        when(userService.findByUserId(anyLong())).thenReturn(community.getUser());
        when(communityRepository.save(any(Community.class))).thenReturn(community);
        when(photoService.uploadPhotoList(anyList())).thenReturn(List.of(community.getPhotoList().get(0)));
        when(photoRepository.save(any(Photo.class))).thenReturn(community.getPhotoList().get(0));

        CommunitySaveRequestDto communitySaveRequestDto = CommunitySaveRequestDtoTest.testCommunitySaveRequestDto(community);
        CommunityResponseDto result = communityService.saveCommunity(communitySaveRequestDto);

        assertNotNull(result);
        assertEquals(community.getUser().getId(), result.getUserId());
        assertEquals(community.getUser().getEmail(), result.getEmail());
        assertEquals(community.getUser().getNickname(), result.getNickname());
        assertEquals(community.getMain().getMainName(), result.getMain());
        assertEquals(community.getSub().getSubName(), result.getSub());
        assertEquals(community.getTitle(), result.getTitle());
        assertEquals(community.getContent(), result.getContent());
        assertEquals(community.getPhotoList().get(0).getId(), result.getPhotoId().get(0));
        assertEquals(community.getPhotoList().get(0).getPhotoUrl(), result.getPhotoUrl().get(0));
        assertEquals(community.getCreatedDateTime(), result.getCreatedDateTime());

        verify(userService).findByUserId(anyLong());
        verify(communityRepository).save(any(Community.class));
        verify(photoService).uploadPhotoList(anyList());
        verify(photoRepository).save(any(Photo.class));
    }

    @Test
    @DisplayName("커뮤니티 카테고리 대분류와 소분류에 따른 게시글 목록 조회 테스트")
    void testGetCommunityList() {
        when(communityRepository.findByMainAndSub(any(), any())).thenReturn(List.of(community));

        List<CommunityListResponseDto> result = communityService.getCommunityList(community.getMain().getMainName(), community.getSub().getSubName());

        assertNotNull(result);
        assertEquals(community.getId(), result.get(0).getCommunityId());
        assertEquals(community.getUser().getId(), result.get(0).getUserId());
        assertEquals(community.getUser().getEmail(), result.get(0).getEmail());
        assertEquals(community.getUser().getNickname(), result.get(0).getNickname());
        assertEquals(community.getMain().getMainName(), result.get(0).getMain());
        assertEquals(community.getSub().getSubName(), result.get(0).getSub());
        assertEquals(community.getTitle(), result.get(0).getTitle());
        assertEquals(community.getContent(), result.get(0).getContent());
        assertEquals(community.getPhotoList().get(0).getId(), result.get(0).getThumbnailId());
        assertEquals(community.getPhotoList().get(0).getPhotoUrl(), result.get(0).getThumbnailUrl());
        assertEquals(community.getCreatedDateTime(), result.get(0).getCreatedDateTime());

        verify(communityRepository).findByMainAndSub(any(), any());
    }

    @Test
    @DisplayName("커뮤니티 게시글 조회 테스트")
    void testFindByCommunityId() {
        when(communityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(community));

        Community result = communityService.findByCommunityId(community.getId());

        assertNotNull(result);
        assertEquals(community.getId(), result.getId());
        assertEquals(community.getMain(), result.getMain());
        assertEquals(community.getSub(), result.getSub());
        assertEquals(community.getTitle(), result.getTitle());
        assertEquals(community.getContent(), result.getContent());

        verify(communityRepository).findById(anyLong());
    }

    @Test
    @DisplayName("커뮤니티 게시글 상세 조회 테스트")
    void testGetCommunity() {
        when(communityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(community));

        CommunityResponseDto result = communityService.getCommunity(community.getId());

        assertNotNull(result);
        assertEquals(community.getId(), result.getCommunityId());
        assertEquals(community.getUser().getId(), result.getUserId());
        assertEquals(community.getUser().getEmail(), result.getEmail());
        assertEquals(community.getUser().getNickname(), result.getNickname());
        assertEquals(community.getMain().getMainName(), result.getMain());
        assertEquals(community.getSub().getSubName(), result.getSub());
        assertEquals(community.getTitle(), result.getTitle());
        assertEquals(community.getContent(), result.getContent());
        assertEquals(community.getPhotoList().get(0).getId(), result.getPhotoId().get(0));
        assertEquals(community.getPhotoList().get(0).getPhotoUrl(), result.getPhotoUrl().get(0));
        assertEquals(community.getCreatedDateTime(), result.getCreatedDateTime());

        verify(communityRepository).findById(anyLong());
    }

    @Test
    @DisplayName("커뮤니티 게시글 수정 테스트")
    void testUpdateCommunity() {
        when(communityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(community));
        when(photoService.uploadPhotoList(anyList())).thenReturn(List.of(community.getPhotoList().get(0)));
        when(photoRepository.save(any(Photo.class))).thenReturn(community.getPhotoList().get(0));

        CommunityUpdateRequestDto communityUpdateRequestDto = CommunityUpdateRequestDtoTest.testCommunityUpdateRequestDto(community);
        CommunityResponseDto result = communityService.updateCommunity(communityUpdateRequestDto);

        assertNotNull(result);
        assertEquals(community.getId(), result.getCommunityId());
        assertEquals(community.getUser().getId(), result.getUserId());
        assertEquals(community.getUser().getEmail(), result.getEmail());
        assertEquals(community.getUser().getNickname(), result.getNickname());
        assertEquals(community.getMain().getMainName(), result.getMain());
        assertEquals(community.getSub().getSubName(), result.getSub());
        assertEquals(community.getTitle(), result.getTitle());
        assertEquals(community.getContent(), result.getContent());
        assertEquals(community.getPhotoList().get(0).getId(), result.getPhotoId().get(0));
        assertEquals(community.getPhotoList().get(0).getPhotoUrl(), result.getPhotoUrl().get(0));
        assertEquals(community.getCreatedDateTime(), result.getCreatedDateTime());

        verify(communityRepository).findById(anyLong());
        verify(photoService).uploadPhotoList(anyList());
        verify(photoRepository).save(any(Photo.class));
    }

    @Test
    @DisplayName("커뮤니티 게시글 삭제 테스트")
    void testDeleteCommunity() {
        when(communityRepository.findById(anyLong())).thenReturn(Optional.ofNullable(community));
        when(photoRepository.findById(anyLong())).thenReturn(Optional.ofNullable(community.getPhotoList().get(0)));
        doNothing().when(photoService).deletePhoto(anyString());
        doNothing().when(photoRepository).delete(any());
        doNothing().when(communityRepository).delete(any());

        communityService.deleteCommunity(community.getId());

        verify(communityRepository).findById(anyLong());
        verify(photoRepository).findById(anyLong());
        verify(photoService).deletePhoto(anyString());
        verify(photoRepository).delete(any());
        verify(communityRepository).delete(any());
    }
}