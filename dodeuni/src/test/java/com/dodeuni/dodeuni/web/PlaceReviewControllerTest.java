package com.dodeuni.dodeuni.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dodeuni.dodeuni.config.SecurityConfig;
import com.dodeuni.dodeuni.domain.place.Place;
import com.dodeuni.dodeuni.domain.place.PlaceReview;
import com.dodeuni.dodeuni.domain.place.PlaceReviewTest;
import com.dodeuni.dodeuni.domain.place.PlaceTest;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.jwt.JwtRequestFilter;
import com.dodeuni.dodeuni.service.place.PlaceReviewService;
import com.dodeuni.dodeuni.web.dto.place.PlaceResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceResponseDtoTest;
import com.dodeuni.dodeuni.web.dto.place.PlaceReviewResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceReviewResponseDtoTest;
import com.dodeuni.dodeuni.web.dto.place.PlaceReviewSaveRequestDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceReviewSaveRequestDtoTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = PlaceReviewController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class PlaceReviewControllerTest {
    private PlaceReview placeReview;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlaceReviewService placeReviewService;

    @BeforeEach
    void setUp() {
        placeReview = PlaceReviewTest.testPlaceReview();
        User user = UserTest.testUser();
        Place place = PlaceTest.testPlace();
        place.setUser(user);
        placeReview.setUser(user);
        placeReview.setPlace(place);
    }

    @Test
    @DisplayName("추천 장소에 리뷰 저장 테스트")
    void testSave() throws Exception {
        PlaceReviewResponseDto placeReviewResponseDto = PlaceReviewResponseDtoTest.testPlaceReviewResponseDto(placeReview);
        when(placeReviewService.saveReview(any())).thenReturn(placeReviewResponseDto);

        PlaceReviewSaveRequestDto placeReviewSaveRequestDto = PlaceReviewSaveRequestDtoTest.testPlaceReviewSaveRequestDto(placeReview);
        mockMvc.perform(post("/api/places/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(placeReviewSaveRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(placeReviewService).saveReview(any());
    }

    @Test
    @DisplayName("추천 장소의 후기 조회 테스트")
    void testView() throws Exception {
        PlaceReviewResponseDto placeReviewResponseDto = PlaceReviewResponseDtoTest.testPlaceReviewResponseDto(placeReview);
        when(placeReviewService.getPlaceReview(anyLong())).thenReturn(placeReviewResponseDto);

        mockMvc.perform(get("/api/places/reviews/{placeReviewId}", placeReview.getId())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(placeReviewService).getPlaceReview(anyLong());
    }

    @Test
    @DisplayName("추천 장소의 후기 삭제 테스트")
    void testDelete() throws Exception {
        PlaceResponseDto placeResponseDto = PlaceResponseDtoTest.testPlaceResponseDto(placeReview.getPlace());
        when(placeReviewService.deleteReview(anyLong(), anyLong())).thenReturn(placeResponseDto);

        mockMvc.perform(delete("/api/places/reviews")
                        .param("placeReviewId", String.valueOf(placeReview.getId()))
                        .param("placeId", String.valueOf(placeReview.getPlace().getId()))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(placeReviewService).deleteReview(anyLong(), anyLong());
    }
}