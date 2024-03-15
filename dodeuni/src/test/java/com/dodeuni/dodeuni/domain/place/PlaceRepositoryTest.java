package com.dodeuni.dodeuni.domain.place;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodeuni.dodeuni.domain.user.UserRepository;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.web.dto.place.PlaceListResponseDto;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PlaceRepositoryTest {
    private Place place;

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        place = PlaceTest.testPlace();
        place.setUser(userRepository.save(UserTest.testUser()));
        place = placeRepository.save(place);
    }

    @AfterEach
    void clean() {
        placeRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("추천 장소 위치와 주소 키워드에 따른 추천 장소 목록 조회 테스트")
    void testGetPlaceByLocationAndKeyword() {
        List<PlaceListResponseDto> foundPlaceList = placeRepository.getPlaceByLocationAndKeyword(place.getX(), place.getY(), place.getAddress());

        assertNotNull(foundPlaceList);
    }

    @Test
    @DisplayName("추천 장소 위치와 이름에 따른 추천 장소 조회 테스트")
    void testFindByNameAndAddressAndXAndY() {
        Place foundPlace = placeRepository.findByNameAndAddressAndXAndY(place.getName(), place.getAddress(), place.getX(), place.getY());

        assertNotNull(foundPlace);
        assertEquals(place.getName(), foundPlace.getName());
        assertEquals(place.getAddress(), foundPlace.getAddress());
        assertEquals(place.getX(), foundPlace.getX());
        assertEquals(place.getY(), foundPlace.getY());
    }
}