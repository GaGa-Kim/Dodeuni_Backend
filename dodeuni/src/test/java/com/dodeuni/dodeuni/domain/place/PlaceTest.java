package com.dodeuni.dodeuni.domain.place;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import com.dodeuni.dodeuni.domain.user.User;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PlaceTest {
    public static final Long PLACE_ID = 1L;
    public static final String PLACE_NAME = "도드니 센터";
    public static final String PLACE_CATEGORY = Category.CENTER.getCategoryName();
    public static final String PLACE_ADDRESS = "서울특별시";
    public static final String PLACE_CONTACT = "02-XXX-XXX";
    public static final double PLACE_X = 1;
    public static final double PLACE_Y = 1;

    public static Place testPlace() {
        Place place = Place.builder()
                .id(PLACE_ID)
                .name(PLACE_NAME)
                .category(PLACE_CATEGORY)
                .address(PLACE_ADDRESS)
                .contact(PLACE_CONTACT)
                .x(PLACE_X)
                .y(PLACE_Y)
                .build();
        place.setCreatedDateTime(LocalDateTime.now());
        place.setModifiedDateTime(LocalDateTime.now());
        return place;
    }

    private Place place;

    @BeforeEach
    void setUp() {
        place = testPlace();
    }

    @Test
    @DisplayName("추천 장소 추가 테스트")
    void testPlaceSave() {
        assertNotNull(place);
        assertEquals(PLACE_ID, place.getId());
        assertEquals(PLACE_NAME, place.getName());
        assertEquals(PLACE_CATEGORY, place.getCategory().getCategoryName());
        assertEquals(PLACE_ADDRESS, place.getAddress());
        assertEquals(PLACE_CONTACT, place.getContact());
        assertEquals(PLACE_X, place.getX());
        assertEquals(PLACE_Y, place.getY());
    }

    @Test
    @DisplayName("추천 장소의 작성자 회원 연관관계 설정 테스트")
    void testSetUser() {
        User user = mock(User.class);
        place.setUser(user);

        assertEquals(user, place.getUser());
    }
}