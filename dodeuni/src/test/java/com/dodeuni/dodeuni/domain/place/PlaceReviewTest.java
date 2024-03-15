package com.dodeuni.dodeuni.domain.place;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import com.dodeuni.dodeuni.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlaceReviewTest {
    public static final Long PLACE_REVIEW_ID = 1L;
    public static final String PLACE_REVIEW_TITLE = "제목";
    public static final String PLACE_REVIEW_CONTENT = "내용";

    public static PlaceReview testPlaceReview() {
        return PlaceReview.builder()
                .id(PLACE_REVIEW_ID)
                .title(PLACE_REVIEW_TITLE)
                .content(PLACE_REVIEW_CONTENT)
                .build();
    }

    private PlaceReview placeReview;

    @BeforeEach
    void setUp() {
        placeReview = testPlaceReview();
    }

    @Test
    @DisplayName("추천 장소 후기 추가 테스트")
    void testPlaceReviewSave() {
        assertNotNull(placeReview);
        assertEquals(PLACE_REVIEW_ID, placeReview.getId());
        assertEquals(PLACE_REVIEW_TITLE, placeReview.getTitle());
        assertEquals(PLACE_REVIEW_CONTENT, placeReview.getContent());
    }

    @Test
    @DisplayName("추천 장소 후기의 작성자 회원 연관관계 설정 테스트")
    void testSetUser() {
        User user = mock(User.class);
        placeReview.setUser(user);

        assertEquals(user, placeReview.getUser());
    }

    @Test
    @DisplayName("추천 장소 후기의 추천 장소 연관관계 설정 테스트")
    void testSetPlace() {
        Place place = mock(Place.class);
        placeReview.setPlace(place);

        assertEquals(place, placeReview.getPlace());
    }
}