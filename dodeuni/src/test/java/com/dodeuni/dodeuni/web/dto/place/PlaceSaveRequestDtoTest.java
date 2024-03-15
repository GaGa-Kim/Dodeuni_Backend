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

public class PlaceSaveRequestDtoTest {
    private final ValidatorUtil<PlaceSaveRequestDto> validatorUtil = new ValidatorUtil<>();

    private Place place;

    public static PlaceSaveRequestDto testPlaceSaveRequestDto(Place place) {
        return PlaceSaveRequestDto.builder()
                .userId(place.getUser().getId())
                .name(place.getName())
                .category(place.getCategory().getCategoryName())
                .address(place.getAddress())
                .contact(place.getContact())
                .x(place.getX())
                .y(place.getY())
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
    @DisplayName("PlaceSaveRequestDto 생성 테스트")
    void testPlaceSaveRequestDtoSave() {
        PlaceSaveRequestDto placeSaveRequestDto = testPlaceSaveRequestDto(place);

        assertNotNull(placeSaveRequestDto);
        assertEquals(place.getUser().getId(), placeSaveRequestDto.getUserId());
        assertEquals(place.getName(), placeSaveRequestDto.getName());
        assertEquals(place.getCategory().getCategoryName(), placeSaveRequestDto.getCategory());
        assertEquals(place.getAddress(), placeSaveRequestDto.getAddress());
        assertEquals(place.getContact(), placeSaveRequestDto.getContact());
        assertEquals(place.getX(), placeSaveRequestDto.getX());
        assertEquals(place.getY(), placeSaveRequestDto.getY());
    }

    @Test
    @DisplayName("PlaceSaveRequestDto toEntity 테스트")
    void testPlaceSaveRequestDtoToEntity() {
        PlaceSaveRequestDto placeSaveRequestDto = testPlaceSaveRequestDto(place);

        Place placeEntity = placeSaveRequestDto.toEntity();
        placeEntity.setUser(place.getUser());
        PlaceReview placeReview = PlaceReviewTest.testPlaceReview();
        placeReview.setPlace(placeEntity);

        assertNotNull(placeSaveRequestDto);
        assertEquals(placeSaveRequestDto.getUserId(), placeEntity.getUser().getId());
        assertEquals(placeSaveRequestDto.getName(), placeEntity.getName());
        assertEquals(placeSaveRequestDto.getCategory(), placeEntity.getCategory().getCategoryName());
        assertEquals(placeSaveRequestDto.getAddress(), placeEntity.getAddress());
        assertEquals(placeSaveRequestDto.getContact(), placeEntity.getContact());
        assertEquals(placeSaveRequestDto.getX(), placeEntity.getX());
        assertEquals(placeSaveRequestDto.getY(), placeEntity.getY());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        PlaceSaveRequestDto placeSaveRequestDto = new PlaceSaveRequestDto();

        assertNotNull(placeSaveRequestDto);
        assertNull(placeSaveRequestDto.getUserId());
        assertNull(placeSaveRequestDto.getName());
        assertNull(placeSaveRequestDto.getCategory());
        assertNull(placeSaveRequestDto.getAddress());
        assertNull(placeSaveRequestDto.getContact());
        assertEquals(0, placeSaveRequestDto.getX());
        assertEquals(0, placeSaveRequestDto.getY());
    }

    @Test
    @DisplayName("회원 아이디 유효성 검증 테스트")
    void userId_validation() {
        PlaceSaveRequestDto placeSaveRequestDto = PlaceSaveRequestDto.builder()
                .userId(null)
                .name(place.getName())
                .category(place.getCategory().getCategoryName())
                .address(place.getAddress())
                .contact(place.getContact())
                .x(place.getX())
                .y(place.getY())
                .build();

        validatorUtil.validate(placeSaveRequestDto);
    }

    @Test
    @DisplayName("추천 장소 이름 유효성 검증 테스트")
    void name_validation() {
        PlaceSaveRequestDto placeSaveRequestDto = PlaceSaveRequestDto.builder()
                .userId(place.getUser().getId())
                .name(INVALID_EMPTY)
                .category(place.getCategory().getCategoryName())
                .address(place.getAddress())
                .contact(place.getContact())
                .x(place.getX())
                .y(place.getY())
                .build();

        validatorUtil.validate(placeSaveRequestDto);
    }

    @Test
    @DisplayName("추천 장소 카테고리 유효성 검증 테스트")
    void category_validation() {
        PlaceSaveRequestDto placeSaveRequestDto = PlaceSaveRequestDto.builder()
                .userId(place.getUser().getId())
                .name(place.getName())
                .category(INVALID_EMPTY)
                .address(place.getAddress())
                .contact(place.getContact())
                .x(place.getX())
                .y(place.getY())
                .build();

        validatorUtil.validate(placeSaveRequestDto);
    }

    @Test
    @DisplayName("추천 장소 주소 유효성 검증 테스트")
    void address_validation() {
        PlaceSaveRequestDto placeSaveRequestDto = PlaceSaveRequestDto.builder()
                .userId(place.getUser().getId())
                .name(place.getName())
                .category(place.getCategory().getCategoryName())
                .address(INVALID_EMPTY)
                .contact(place.getContact())
                .x(place.getX())
                .y(place.getY())
                .build();

        validatorUtil.validate(placeSaveRequestDto);
    }

    @Test
    @DisplayName("추천 장소 경도 유효성 검증 테스트")
    void x_validation() {
        PlaceSaveRequestDto placeSaveRequestDto = PlaceSaveRequestDto.builder()
                .userId(place.getUser().getId())
                .name(place.getName())
                .category(place.getCategory().getCategoryName())
                .address(place.getAddress())
                .contact(place.getContact())
                .x(0)
                .y(place.getY())
                .build();

        validatorUtil.validate(placeSaveRequestDto);
    }

    @Test
    @DisplayName("추천 장소 위도 유효성 검증 테스트")
    void y_validation() {
        PlaceSaveRequestDto placeSaveRequestDto = PlaceSaveRequestDto.builder()
                .userId(place.getUser().getId())
                .name(place.getName())
                .category(place.getCategory().getCategoryName())
                .address(place.getAddress())
                .contact(place.getContact())
                .x(place.getX())
                .y(0)
                .build();

        validatorUtil.validate(placeSaveRequestDto);
    }
}