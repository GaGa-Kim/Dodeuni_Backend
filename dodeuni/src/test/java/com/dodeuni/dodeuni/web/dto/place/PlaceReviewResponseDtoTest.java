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

public class PlaceReviewResponseDtoTest {
    private PlaceReview placeReview;

    public static PlaceReviewResponseDto testPlaceReviewResponseDto(PlaceReview placeReview) {
        return new PlaceReviewResponseDto(placeReview);
    }

    @BeforeEach
    void setUp() {
        Place place = PlaceTest.testPlace();
        User user = UserTest.testUser();
        place.setUser(user);
        placeReview = PlaceReviewTest.testPlaceReview();
        placeReview.setUser(user);
        placeReview.setPlace(place);
    }

    @Test
    @DisplayName("PlaceReviewResponseDto 생성 테스트")
    void testPlaceReviewResponseDtoSave() {
        PlaceReviewResponseDto placeReviewResponseDto = testPlaceReviewResponseDto(placeReview);

        assertNotNull(placeReviewResponseDto);
        assertEquals(placeReview.getId(), placeReviewResponseDto.getPlaceId());
        assertEquals(placeReview.getUser().getId(), placeReviewResponseDto.getUserId());
        assertEquals(placeReview.getUser().getNickname(), placeReviewResponseDto.getNickname());
        assertEquals(placeReview.getTitle(), placeReviewResponseDto.getTitle());
        assertEquals(placeReview.getContent(), placeReviewResponseDto.getContent());
        assertEquals(placeReview.getCreatedDateTime(), placeReviewResponseDto.getCreatedDateTime());
        assertEquals(placeReview.getModifiedDateTime(), placeReviewResponseDto.getModifiedDateTime());
    }
}