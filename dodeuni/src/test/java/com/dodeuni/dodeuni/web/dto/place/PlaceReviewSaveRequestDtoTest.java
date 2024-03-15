package com.dodeuni.dodeuni.web.dto.place;

import static com.dodeuni.dodeuni.web.dto.auth.UserSaveRequestDtoTest.INVALID_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.dodeuni.dodeuni.domain.place.Place;
import com.dodeuni.dodeuni.domain.place.PlaceReview;
import com.dodeuni.dodeuni.domain.place.PlaceReviewTest;
import com.dodeuni.dodeuni.domain.place.PlaceTest;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.web.dto.ValidatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlaceReviewSaveRequestDtoTest {
    private final ValidatorUtil<PlaceReviewSaveRequestDto> validatorUtil = new ValidatorUtil<>();

    private PlaceReview placeReview;

    public static PlaceReviewSaveRequestDto testPlaceReviewSaveRequestDto(PlaceReview placeReview) {
        return PlaceReviewSaveRequestDto.builder()
                .placeId(placeReview.getId())
                .userId(placeReview.getUser().getId())
                .title(placeReview.getTitle())
                .content(placeReview.getContent())
                .build();
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
    @DisplayName("PlaceReviewSaveRequestDto 생성 테스트")
    void testPlaceReviewSaveRequestDtoSave() {
        PlaceReviewSaveRequestDto placeReviewSaveRequestDto = testPlaceReviewSaveRequestDto(placeReview);

        assertNotNull(placeReviewSaveRequestDto);
        assertEquals(placeReview.getId(), placeReviewSaveRequestDto.getPlaceId());
        assertEquals(placeReview.getUser().getId(), placeReviewSaveRequestDto.getUserId());
        assertEquals(placeReview.getTitle(), placeReviewSaveRequestDto.getTitle());
        assertEquals(placeReview.getContent(), placeReviewSaveRequestDto.getContent());
    }

    @Test
    @DisplayName("PlaceReviewSaveRequestDto toEntity 테스트")
    void testPlaceReviewSaveRequestDtoToEntity() {
        PlaceReviewSaveRequestDto placeReviewSaveRequestDto = testPlaceReviewSaveRequestDto(placeReview);

        PlaceReview placeReviewEntity = placeReviewSaveRequestDto.toEntity();
        placeReviewEntity.setUser(placeReview.getUser());
        placeReviewEntity.setPlace(placeReview.getPlace());

        assertNotNull(placeReviewSaveRequestDto);
        assertEquals(placeReviewSaveRequestDto.getPlaceId(), placeReviewEntity.getPlace().getId());
        assertEquals(placeReviewSaveRequestDto.getUserId(), placeReviewEntity.getUser().getId());
        assertEquals(placeReviewSaveRequestDto.getTitle(), placeReviewEntity.getTitle());
        assertEquals(placeReviewSaveRequestDto.getContent(), placeReviewEntity.getContent());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        PlaceReviewSaveRequestDto placeReviewSaveRequestDto = new PlaceReviewSaveRequestDto();

        assertNotNull(placeReviewSaveRequestDto);
        assertNull(placeReviewSaveRequestDto.getPlaceId());
        assertNull(placeReviewSaveRequestDto.getUserId());
        assertNull(placeReviewSaveRequestDto.getTitle());
        assertNull(placeReviewSaveRequestDto.getContent());
    }

    @Test
    @DisplayName("장소 아이디 유효성 검증 테스트")
    void placeId_validation() {
        PlaceReviewSaveRequestDto placeReviewSaveRequestDto = PlaceReviewSaveRequestDto.builder()
                .placeId(null)
                .userId(placeReview.getUser().getId())
                .title(placeReview.getTitle())
                .content(placeReview.getContent())
                .build();

        validatorUtil.validate(placeReviewSaveRequestDto);
    }

    @Test
    @DisplayName("회원 아이디 유효성 검증 테스트")
    void userId_validation() {
        PlaceReviewSaveRequestDto placeReviewSaveRequestDto = PlaceReviewSaveRequestDto.builder()
                .placeId(placeReview.getId())
                .userId(null)
                .title(placeReview.getTitle())
                .content(placeReview.getContent())
                .build();

        validatorUtil.validate(placeReviewSaveRequestDto);
    }

    @Test
    @DisplayName("제목 유효성 검증 테스트")
    void title_validation() {
        PlaceReviewSaveRequestDto placeReviewSaveRequestDto = PlaceReviewSaveRequestDto.builder()
                .placeId(placeReview.getId())
                .userId(placeReview.getUser().getId())
                .title(INVALID_EMPTY)
                .content(placeReview.getContent())
                .build();

        validatorUtil.validate(placeReviewSaveRequestDto);
    }

    @Test
    @DisplayName("내용 유효성 검증 테스트")
    void content_validation() {
        PlaceReviewSaveRequestDto placeReviewSaveRequestDto = PlaceReviewSaveRequestDto.builder()
                .placeId(placeReview.getId())
                .userId(placeReview.getUser().getId())
                .title(placeReview.getTitle())
                .content(INVALID_EMPTY)
                .build();

        validatorUtil.validate(placeReviewSaveRequestDto);
    }
}