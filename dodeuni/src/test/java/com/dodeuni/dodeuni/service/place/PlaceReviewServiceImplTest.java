package com.dodeuni.dodeuni.service.place;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dodeuni.dodeuni.domain.place.Place;
import com.dodeuni.dodeuni.domain.place.PlaceReview;
import com.dodeuni.dodeuni.domain.place.PlaceReviewRepository;
import com.dodeuni.dodeuni.domain.place.PlaceReviewTest;
import com.dodeuni.dodeuni.domain.place.PlaceTest;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.service.user.UserService;
import com.dodeuni.dodeuni.web.dto.place.PlaceResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceReviewResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceReviewSaveRequestDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceReviewSaveRequestDtoTest;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlaceReviewServiceImplTest {
    private PlaceReview placeReview;

    @Mock
    private PlaceReviewRepository placeReviewRepository;
    @Mock
    private UserService userService;
    @Mock
    private PlaceService placeService;
    @InjectMocks
    private PlaceReviewServiceImpl placeReviewService;

    @BeforeEach
    void setUp() {
        placeReview = PlaceReviewTest.testPlaceReview();
        User user = UserTest.testUser();
        Place place = PlaceTest.testPlace();
        place.setUser(user);
        placeReview.setUser(user);
        placeReview.setPlace(place);
    }

    @Test
    @DisplayName("추천 장소 후기 저장 테스트")
    void testSaveReview() {
        when(placeService.findByPlaceId(anyLong())).thenReturn(placeReview.getPlace());
        when(userService.findByUserId(anyLong())).thenReturn(placeReview.getUser());
        when(placeReviewRepository.save(any(PlaceReview.class))).thenReturn(placeReview);

        PlaceReviewSaveRequestDto placeReviewSaveRequestDto = PlaceReviewSaveRequestDtoTest.testPlaceReviewSaveRequestDto(placeReview);
        PlaceReviewResponseDto result = placeReviewService.saveReview(placeReviewSaveRequestDto);

        assertNotNull(result);
        assertEquals(placeReview.getId(), result.getPlaceId());
        assertEquals(placeReview.getUser().getId(), result.getUserId());
        assertEquals(placeReview.getUser().getNickname(), result.getNickname());
        assertEquals(placeReview.getTitle(), result.getTitle());
        assertEquals(placeReview.getContent(), result.getContent());

        verify(placeService).findByPlaceId(anyLong());
        verify(userService).findByUserId(anyLong());
        verify(placeReviewRepository).save(any(PlaceReview.class));
    }

    @Test
    @DisplayName("추천 장소 후기 조회 테스트")
    void testGetPlaceReview() {
        when(placeReviewRepository.findById(anyLong())).thenReturn(Optional.ofNullable(placeReview));

        PlaceReviewResponseDto result = placeReviewService.getPlaceReview(placeReview.getId());

        assertNotNull(result);
        assertEquals(placeReview.getId(), result.getPlaceId());
        assertEquals(placeReview.getUser().getId(), result.getUserId());
        assertEquals(placeReview.getUser().getNickname(), result.getNickname());
        assertEquals(placeReview.getTitle(), result.getTitle());
        assertEquals(placeReview.getContent(), result.getContent());
        assertEquals(placeReview.getCreatedDateTime(), result.getCreatedDateTime());
        assertEquals(placeReview.getModifiedDateTime(), result.getModifiedDateTime());

        verify(placeReviewRepository).findById(anyLong());
    }

    @Test
    @DisplayName("추천 장소 후기 삭제 테스트")
    void testDeleteReview() {
        doNothing().when(placeReviewRepository).deleteById(anyLong());
        when(placeService.findByPlaceId(anyLong())).thenReturn(placeReview.getPlace());

        PlaceResponseDto result = placeReviewService.deleteReview(placeReview.getId(), placeReview.getPlace().getId());

        assertNotNull(result);
        assertEquals(placeReview.getPlace().getId(), result.getPlaceId());
        assertEquals(placeReview.getPlace().getUser().getId(), result.getUserId());
        assertEquals(placeReview.getPlace().getUser().getNickname(), result.getNickname());
        assertEquals(placeReview.getPlace().getName(), result.getName());
        assertEquals(placeReview.getPlace().getCategory().getCategoryName(), result.getCategory());
        assertEquals(placeReview.getPlace().getAddress(), result.getAddress());
        assertEquals(placeReview.getPlace().getContact(), result.getContact());
        assertEquals(placeReview.getPlace().getX(), result.getX());
        assertEquals(placeReview.getPlace().getY(), result.getY());
        assertEquals(placeReview.getPlace().getPlaceReviewList().size(), result.getReviews().size());
        assertEquals(placeReview.getPlace().getCreatedDateTime(), result.getCreatedDateTime());
        assertEquals(placeReview.getPlace().getModifiedDateTime(), result.getModifiedDateTime());

        verify(placeReviewRepository).deleteById(anyLong());
        verify(placeService).findByPlaceId(anyLong());
    }
}