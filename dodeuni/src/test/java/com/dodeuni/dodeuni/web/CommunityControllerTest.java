package com.dodeuni.dodeuni.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dodeuni.dodeuni.config.SecurityConfig;
import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.domain.community.CommunityTest;
import com.dodeuni.dodeuni.domain.community.Photo;
import com.dodeuni.dodeuni.domain.community.PhotoTest;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.jwt.JwtRequestFilter;
import com.dodeuni.dodeuni.service.community.CommunityService;
import com.dodeuni.dodeuni.web.dto.community.CommunityListResponseDto;
import com.dodeuni.dodeuni.web.dto.community.CommunityListResponseDtoTest;
import com.dodeuni.dodeuni.web.dto.community.CommunityResponseDto;
import com.dodeuni.dodeuni.web.dto.community.CommunityResponseDtoTest;
import com.dodeuni.dodeuni.web.dto.community.CommunitySaveRequestDto;
import com.dodeuni.dodeuni.web.dto.community.CommunitySaveRequestDtoTest;
import com.dodeuni.dodeuni.web.dto.community.CommunityUpdateRequestDto;
import com.dodeuni.dodeuni.web.dto.community.CommunityUpdateRequestDtoTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

@WebMvcTest(controllers = CommunityController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class CommunityControllerTest {
    private Community community;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CommunityService communityService;

    @BeforeEach
    void setUp() {
        community = CommunityTest.testCommunity();
        community.setUser(UserTest.testUser());
        Photo photo = PhotoTest.testPhoto();
        photo.setCommunity(community);
    }

    @Test
    @DisplayName("커뮤니티 게시글 저장 테스트")
    void testSave() throws Exception {
        CommunityResponseDto communityResponseDto = CommunityResponseDtoTest.testCommunityResponseDto(community);
        when(communityService.saveCommunity(any())).thenReturn(communityResponseDto);

        CommunitySaveRequestDto communitySaveRequestDto = CommunitySaveRequestDtoTest.testCommunitySaveRequestDto(community);
        mockMvc.perform(multipart("/api/communities")
                        .file((MockMultipartFile) communitySaveRequestDto.getPhotos().get(0))
                        .param("userId", String.valueOf(communitySaveRequestDto.getUserId()))
                        .param("main", communitySaveRequestDto.getMain())
                        .param("sub", communitySaveRequestDto.getSub())
                        .param("title", communitySaveRequestDto.getTitle())
                        .param("content", communitySaveRequestDto.getContent())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(communityService).saveCommunity(any());
    }

    @Test
    @DisplayName("커뮤니티 카테고리 대분류와 소분류에 따른 게시글 목록 조회 테스트")
    void testList() throws Exception {
        List<CommunityListResponseDto> communityListResponseDtoList = List.of(CommunityListResponseDtoTest.testCommunityListResponseDto(community));
        when(communityService.getCommunityList(anyString(), anyString())).thenReturn(communityListResponseDtoList);

        mockMvc.perform(get("/api/communities/list")
                        .param("main", community.getMain().getMainName())
                        .param("sub", community.getSub().getSubName())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(communityService).getCommunityList(anyString(), anyString());
    }

    @Test
    @DisplayName("커뮤니티 게시글 상세 조회 테스트")
    void testView() throws Exception {
        CommunityResponseDto communityResponseDto = CommunityResponseDtoTest.testCommunityResponseDto(community);
        when(communityService.getCommunity(any())).thenReturn(communityResponseDto);

        mockMvc.perform(get("/api/communities/{communityId}", community.getId())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(communityService).getCommunity(any());
    }

    @Test
    @DisplayName("커뮤니티 게시글 수정 테스트")
    void testUpdate() throws Exception {
        CommunityResponseDto communityResponseDto = CommunityResponseDtoTest.testCommunityResponseDto(community);
        when(communityService.updateCommunity(any())).thenReturn(communityResponseDto);

        MockMultipartHttpServletRequestBuilder builder = multipart("/api/communities");
        builder.with(new RequestPostProcessor() {
            @Override
            public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
                request.setMethod("PUT");
                return request;
            }
        });

        CommunityUpdateRequestDto communityUpdateRequestDto = CommunityUpdateRequestDtoTest.testCommunityUpdateRequestDto(community);
        mockMvc.perform(builder
                        .file((MockMultipartFile) communityUpdateRequestDto.getAddPhoto().get(0))
                        .param("communityId", String.valueOf(communityUpdateRequestDto.getCommunityId()))
                        .param("userId", String.valueOf(communityUpdateRequestDto.getUserId()))
                        .param("main", communityUpdateRequestDto.getMain())
                        .param("sub", communityUpdateRequestDto.getSub())
                        .param("title", communityUpdateRequestDto.getTitle())
                        .param("content", communityUpdateRequestDto.getContent())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(communityService).updateCommunity(any());
    }

    @Test
    @DisplayName("커뮤니티 게시글 삭제 테스트")
    void testDelete() throws Exception {
        doNothing().when(communityService).deleteCommunity(anyLong());

        mockMvc.perform(delete("/api/communities")
                        .param("communityId", String.valueOf(community.getId()))
                        .with(csrf()))
                .andExpect(status().is2xxSuccessful());

        verify(communityService).deleteCommunity(anyLong());
    }
}