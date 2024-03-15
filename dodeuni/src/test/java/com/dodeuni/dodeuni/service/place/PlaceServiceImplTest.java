package com.dodeuni.dodeuni.service.place;

import static com.dodeuni.dodeuni.web.dto.place.PlaceListResponseDtoTest.PLACE_DISTANCE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dodeuni.dodeuni.domain.place.Place;
import com.dodeuni.dodeuni.domain.place.PlaceRepository;
import com.dodeuni.dodeuni.domain.place.PlaceTest;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.service.user.UserService;
import com.dodeuni.dodeuni.web.dto.place.PlaceInquiryDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceInquiryDtoTest;
import com.dodeuni.dodeuni.web.dto.place.PlaceListResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceListResponseDtoTest;
import com.dodeuni.dodeuni.web.dto.place.PlaceResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceSaveRequestDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceSaveRequestDtoTest;
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
class PlaceServiceImplTest {
    private Place place;

    @Mock
    private PlaceRepository placeRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private PlaceServiceImpl placeService;

    @BeforeEach
    void setUp() {
        place = PlaceTest.testPlace();
        place.setUser(UserTest.testUser());
    }

    @Test
    @DisplayName("추천 장소 저장 테스트")
    void testSavePlace() {
        when(placeRepository.findByNameAndAddressAndXAndY(anyString(), anyString(), anyDouble(), anyDouble())).thenReturn(null);
        when(userService.findByUserId(anyLong())).thenReturn(place.getUser());
        when(placeRepository.save(any(Place.class))).thenReturn(place);

        PlaceSaveRequestDto placeSaveRequestDto = PlaceSaveRequestDtoTest.testPlaceSaveRequestDto(place);
        PlaceResponseDto result = placeService.savePlace(placeSaveRequestDto);

        assertNotNull(result);
        assertEquals(place.getUser().getId(), result.getUserId());
        assertEquals(place.getUser().getNickname(), result.getNickname());
        assertEquals(place.getName(), result.getName());
        assertEquals(place.getCategory().getCategoryName(), result.getCategory());
        assertEquals(place.getAddress(), result.getAddress());
        assertEquals(place.getContact(), result.getContact());
        assertEquals(place.getX(), result.getX());
        assertEquals(place.getY(), result.getY());
        assertEquals(place.getPlaceReviewList().size(), result.getReviews().size());

        verify(placeRepository).findByNameAndAddressAndXAndY(anyString(), anyString(), anyDouble(), anyDouble());
        verify(userService).findByUserId(anyLong());
        verify(placeRepository).save(any(Place.class));
    }

    @Test
    @DisplayName("이미 존재하는 추천 장소 저장 테스트")
    void testSavePlaceWithExisting() {
        when(placeRepository.findByNameAndAddressAndXAndY(anyString(), anyString(), anyDouble(), anyDouble())).thenReturn(place);

        PlaceSaveRequestDto placeSaveRequestDto = PlaceSaveRequestDtoTest.testPlaceSaveRequestDto(place);
        PlaceResponseDto result = placeService.savePlace(placeSaveRequestDto);

        assertNotNull(result);
        assertEquals(place.getId(), result.getPlaceId());
        assertEquals(place.getUser().getId(), result.getUserId());
        assertEquals(place.getUser().getNickname(), result.getNickname());
        assertEquals(place.getName(), result.getName());
        assertEquals(place.getCategory().getCategoryName(), result.getCategory());
        assertEquals(place.getAddress(), result.getAddress());
        assertEquals(place.getContact(), result.getContact());
        assertEquals(place.getX(), result.getX());
        assertEquals(place.getY(), result.getY());
        assertEquals(place.getPlaceReviewList().size(), result.getReviews().size());
        assertEquals(place.getCreatedDateTime(), result.getCreatedDateTime());
        assertEquals(place.getModifiedDateTime(), result.getModifiedDateTime());

        verify(placeRepository).findByNameAndAddressAndXAndY(anyString(), anyString(), anyDouble(), anyDouble());
    }

    @Test
    @DisplayName("추천 장소 목록 조회 테스트")
    void testGetPlaceList() {
        when(placeRepository.getPlaceByLocationAndKeyword(anyDouble(), anyDouble(), anyString())).thenReturn(List.of(PlaceListResponseDtoTest.testPlaceListResponseDto(place)));

        PlaceInquiryDto placeInquiryDto = PlaceInquiryDtoTest.testPlaceInquiryDto(place);
        List<PlaceListResponseDto> result = placeService.getPlaceList(placeInquiryDto);

        assertNotNull(result.get(0));
        assertEquals(place.getId(), result.get(0).getPlaceId());
        assertEquals(place.getUser().getId(), result.get(0).getUserId());
        assertEquals(place.getUser().getNickname(), result.get(0).getNickname());
        assertEquals(place.getName(), result.get(0).getName());
        assertEquals(place.getCategory().getCategoryName(), result.get(0).getCategory());
        assertEquals(place.getAddress(), result.get(0).getAddress());
        assertEquals(place.getContact(), result.get(0).getContact());
        assertEquals(place.getX(), result.get(0).getX());
        assertEquals(place.getY(), result.get(0).getY());
        assertEquals(PLACE_DISTANCE, 0, result.get(0).getDistance());
        assertEquals(place.getPlaceReviewList().size(), result.get(0).getReviewsCount());
        assertEquals(place.getCreatedDateTime(), result.get(0).getCreatedDateTime());
        assertEquals(place.getModifiedDateTime(), result.get(0).getModifiedDateTime());

        verify(placeRepository).getPlaceByLocationAndKeyword(anyDouble(), anyDouble(), anyString());
    }

    @Test
    @DisplayName("추천 장소 상세 조회 테스트")
    void testFindByPlaceId() {
        when(placeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(place));

        Place result = placeService.findByPlaceId(place.getId());

        assertNotNull(result);
        assertEquals(place.getId(), result.getId());
        assertEquals(place.getName(), result.getName());
        assertEquals(place.getCategory(), result.getCategory());
        assertEquals(place.getAddress(), result.getAddress());
        assertEquals(place.getContact(), result.getContact());
        assertEquals(place.getX(), result.getX());
        assertEquals(place.getY(), result.getY());

        verify(placeRepository).findById(anyLong());
    }

    @Test
    @DisplayName("추천 장소 조회 테스트")
    void testGetPlace() {
        when(placeRepository.findById(anyLong())).thenReturn(Optional.ofNullable(place));

        PlaceResponseDto result = placeService.getPlace(place.getId());

        assertNotNull(result);
        assertEquals(place.getId(), result.getPlaceId());
        assertEquals(place.getUser().getId(), result.getUserId());
        assertEquals(place.getUser().getNickname(), result.getNickname());
        assertEquals(place.getName(), result.getName());
        assertEquals(place.getCategory().getCategoryName(), result.getCategory());
        assertEquals(place.getAddress(), result.getAddress());
        assertEquals(place.getContact(), result.getContact());
        assertEquals(place.getX(), result.getX());
        assertEquals(place.getY(), result.getY());
        assertEquals(place.getPlaceReviewList().size(), result.getReviews().size());
        assertEquals(place.getCreatedDateTime(), result.getCreatedDateTime());
        assertEquals(place.getModifiedDateTime(), result.getModifiedDateTime());

        verify(placeRepository).findById(anyLong());
    }
}