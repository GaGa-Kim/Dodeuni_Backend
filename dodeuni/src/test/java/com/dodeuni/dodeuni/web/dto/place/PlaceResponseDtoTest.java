package com.dodeuni.dodeuni.web.dto.place;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodeuni.dodeuni.domain.place.Place;
import com.dodeuni.dodeuni.domain.place.PlaceReview;
import com.dodeuni.dodeuni.domain.place.PlaceReviewTest;
import com.dodeuni.dodeuni.domain.place.PlaceTest;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlaceResponseDtoTest {
    private Place place;

    public static PlaceResponseDto testPlaceResponseDto(Place place) {
        return new PlaceResponseDto(place);
    }

    @BeforeEach
    void setUp() {
        place = PlaceTest.testPlace();
        User user = UserTest.testUser();
        place.setUser(user);
        PlaceReview placeReview = PlaceReviewTest.testPlaceReview();
        placeReview.setUser(user);
        placeReview.setPlace(place);
    }

    @Test
    @DisplayName("PlaceResponseDto 생성 테스트")
    void testPlaceResponseDtoSave() {
        PlaceResponseDto placeResponseDto = testPlaceResponseDto(place);

        assertNotNull(placeResponseDto);
        assertEquals(place.getId(), placeResponseDto.getPlaceId());
        assertEquals(place.getUser().getId(), placeResponseDto.getUserId());
        assertEquals(place.getUser().getNickname(), placeResponseDto.getNickname());
        assertEquals(place.getName(), placeResponseDto.getName());
        assertEquals(place.getCategory().getCategoryName(), placeResponseDto.getCategory());
        assertEquals(place.getAddress(), placeResponseDto.getAddress());
        assertEquals(place.getContact(), placeResponseDto.getContact());
        assertEquals(place.getX(), placeResponseDto.getX());
        assertEquals(place.getY(), placeResponseDto.getY());
        assertEquals(place.getPlaceReviewList().size(), placeResponseDto.getReviews().size());
        assertEquals(place.getCreatedDateTime(), placeResponseDto.getCreatedDateTime());
        assertEquals(place.getModifiedDateTime(), placeResponseDto.getModifiedDateTime());
    }
}