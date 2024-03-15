package com.dodeuni.dodeuni.web.dto.place;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodeuni.dodeuni.domain.place.Place;
import com.dodeuni.dodeuni.domain.place.PlaceReview;
import com.dodeuni.dodeuni.domain.place.PlaceReviewTest;
import com.dodeuni.dodeuni.domain.place.PlaceTest;
import com.dodeuni.dodeuni.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlaceListResponseDtoTest {
    public static final double PLACE_DISTANCE = 1.0;
    private Place place;

    public static PlaceListResponseDto testPlaceListResponseDto(Place place) {
        return new PlaceListResponseDto(place.getId(), place.getName(), place.getCategory(),
                place.getAddress(), place.getContact(), place.getX(), place.getY(),
                place.getCreatedDateTime(), place.getModifiedDateTime(),
                place.getUser().getId(), place.getUser().getNickname(), PLACE_DISTANCE, (long) place.getPlaceReviewList().size());
    }

    @BeforeEach
    void setUp() {
        place = PlaceTest.testPlace();
        place.setUser(UserTest.testUser());
        PlaceReview placeReview = PlaceReviewTest.testPlaceReview();
        placeReview.setPlace(place);
    }

    @Test
    @DisplayName("PlaceListResponseDto 생성 테스트")
    void testPlaceListResponseDtoSave() {
        PlaceListResponseDto placeListResponseDto = testPlaceListResponseDto(place);

        assertNotNull(placeListResponseDto);
        assertEquals(place.getId(), placeListResponseDto.getPlaceId());
        assertEquals(place.getUser().getId(), placeListResponseDto.getUserId());
        assertEquals(place.getUser().getNickname(), placeListResponseDto.getNickname());
        assertEquals(place.getName(), placeListResponseDto.getName());
        assertEquals(place.getCategory().getCategoryName(), placeListResponseDto.getCategory());
        assertEquals(place.getAddress(), placeListResponseDto.getAddress());
        assertEquals(place.getContact(), placeListResponseDto.getContact());
        assertEquals(place.getX(), placeListResponseDto.getX());
        assertEquals(place.getY(), placeListResponseDto.getY());
        assertEquals(PLACE_DISTANCE, 0, placeListResponseDto.getDistance());
        assertEquals(place.getPlaceReviewList().size(), placeListResponseDto.getReviewsCount());
        assertEquals(place.getCreatedDateTime(), placeListResponseDto.getCreatedDateTime());
        assertEquals(place.getModifiedDateTime(), placeListResponseDto.getModifiedDateTime());
    }
}