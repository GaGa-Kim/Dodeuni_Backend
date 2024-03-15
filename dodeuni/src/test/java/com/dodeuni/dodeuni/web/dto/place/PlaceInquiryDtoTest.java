package com.dodeuni.dodeuni.web.dto.place;

import static com.dodeuni.dodeuni.web.dto.auth.UserSaveRequestDtoTest.INVALID_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.dodeuni.dodeuni.domain.place.Place;
import com.dodeuni.dodeuni.domain.place.PlaceReview;
import com.dodeuni.dodeuni.domain.place.PlaceReviewTest;
import com.dodeuni.dodeuni.domain.place.PlaceTest;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.web.dto.ValidatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlaceInquiryDtoTest {
    private final ValidatorUtil<PlaceInquiryDto> validatorUtil = new ValidatorUtil<>();

    private Place place;

    public static PlaceInquiryDto testPlaceInquiryDto(Place place) {
        return PlaceInquiryDto.builder()
                .x(place.getX())
                .y(place.getY())
                .keyword(place.getAddress())
                .build();
    }

    @BeforeEach
    void setUp() {
        place = PlaceTest.testPlace();
        place.setUser(UserTest.testUser());
        PlaceReview placeReview = PlaceReviewTest.testPlaceReview();
        placeReview.setPlace(place);
    }

    @Test
    @DisplayName("PlaceInquiryDto 생성 테스트")
    void testPlaceInquiryDtoSave() {
        PlaceInquiryDto placeInquiryDto = testPlaceInquiryDto(place);

        assertNotNull(placeInquiryDto);
        assertEquals(place.getX(), placeInquiryDto.getX());
        assertEquals(place.getY(), placeInquiryDto.getY());
        assertEquals(place.getAddress(), placeInquiryDto.getKeyword());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        PlaceInquiryDto placeInquiryDto = new PlaceInquiryDto();

        assertNotNull(placeInquiryDto);
        assertEquals(0, placeInquiryDto.getX());
        assertEquals(0, placeInquiryDto.getY());
        assertNull(placeInquiryDto.getKeyword());
    }

    @Test
    @DisplayName("회원 경도 유효성 검증 테스트")
    void x_validation() {
        PlaceInquiryDto placeInquiryDto = PlaceInquiryDto.builder()
                .x(0)
                .y(place.getY())
                .keyword(place.getAddress())
                .build();

        validatorUtil.validate(placeInquiryDto);
    }

    @Test
    @DisplayName("회원 위도 유효성 검증 테스트")
    void y_validation() {
        PlaceInquiryDto placeInquiryDto = PlaceInquiryDto.builder()
                .x(place.getX())
                .y(0)
                .keyword(place.getAddress())
                .build();

        validatorUtil.validate(placeInquiryDto);
    }

    @Test
    @DisplayName("검색 지역 유효성 검증 테스트")
    void keyword_validation() {
        PlaceInquiryDto placeInquiryDto = PlaceInquiryDto.builder()
                .x(place.getX())
                .y(place.getY())
                .keyword(INVALID_EMPTY)
                .build();

        validatorUtil.validate(placeInquiryDto);
    }
}