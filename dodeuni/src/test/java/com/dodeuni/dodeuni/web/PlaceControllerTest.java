package com.dodeuni.dodeuni.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dodeuni.dodeuni.config.SecurityConfig;
import com.dodeuni.dodeuni.domain.place.Place;
import com.dodeuni.dodeuni.domain.place.PlaceTest;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.jwt.JwtRequestFilter;
import com.dodeuni.dodeuni.service.place.PlaceService;
import com.dodeuni.dodeuni.web.dto.place.PlaceInquiryDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceInquiryDtoTest;
import com.dodeuni.dodeuni.web.dto.place.PlaceListResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceListResponseDtoTest;
import com.dodeuni.dodeuni.web.dto.place.PlaceResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceResponseDtoTest;
import com.dodeuni.dodeuni.web.dto.place.PlaceSaveRequestDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceSaveRequestDtoTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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

@WebMvcTest(controllers = PlaceController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class PlaceControllerTest {
    private Place place;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlaceService placeService;

    @BeforeEach
    void setUp() {
        place = PlaceTest.testPlace();
        place.setUser(UserTest.testUser());
    }

    @Test
    @DisplayName("추천 장소 저장 테스트")
    void testSave() throws Exception {
        PlaceResponseDto placeResponseDto = PlaceResponseDtoTest.testPlaceResponseDto(place);
        when(placeService.savePlace(any())).thenReturn(placeResponseDto);

        PlaceSaveRequestDto placeSaveRequestDto = PlaceSaveRequestDtoTest.testPlaceSaveRequestDto(place);
        mockMvc.perform(post("/api/places")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(placeSaveRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(placeService).savePlace(any());
    }

    @Test
    @DisplayName("추천 장소 목록 조회 테스트")
    void testList() throws Exception {
        List<PlaceListResponseDto> placeListResponseDtoList = List.of(PlaceListResponseDtoTest.testPlaceListResponseDto(place));
        when(placeService.getPlaceList(any())).thenReturn(placeListResponseDtoList);

        PlaceInquiryDto placeInquiryDto = PlaceInquiryDtoTest.testPlaceInquiryDto(place);
        mockMvc.perform(post("/api/places/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(placeInquiryDto))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(placeService).getPlaceList(any());
    }

    @Test
    @DisplayName("추천 장소 상세 조회")
    void testView() throws Exception {
        PlaceResponseDto placeResponseDto = PlaceResponseDtoTest.testPlaceResponseDto(place);
        when(placeService.getPlace(anyLong())).thenReturn(placeResponseDto);

        mockMvc.perform(get("/api/places/{placeId}", place.getId())
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(placeService).getPlace(anyLong());
    }
}