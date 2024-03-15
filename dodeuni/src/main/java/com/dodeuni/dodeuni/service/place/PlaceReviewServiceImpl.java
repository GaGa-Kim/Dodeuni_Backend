package com.dodeuni.dodeuni.service.place;

import com.dodeuni.dodeuni.domain.place.Place;
import com.dodeuni.dodeuni.domain.place.PlaceReview;
import com.dodeuni.dodeuni.domain.place.PlaceReviewRepository;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.except.NotFoundException;
import com.dodeuni.dodeuni.service.user.UserService;
import com.dodeuni.dodeuni.web.dto.place.PlaceResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceReviewResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceReviewSaveRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceReviewServiceImpl implements PlaceReviewService {
    private final PlaceReviewRepository placeReviewRepository;
    private final UserService userService;
    private final PlaceService placeService;

    @Override
    public PlaceReviewResponseDto saveReview(PlaceReviewSaveRequestDto requestDto) {
        Place place = placeService.findByPlaceId(requestDto.getPlaceId());
        User user = userService.findByUserId(requestDto.getUserId());
        PlaceReview placeReview = requestDto.toEntity();
        placeReview.setPlace(place);
        placeReview.setUser(user);
        placeReviewRepository.save(placeReview);
        return new PlaceReviewResponseDto(placeReview);
    }

    @Override
    public PlaceReviewResponseDto getPlaceReview(Long placeReviewId) {
        PlaceReview placeReview = findByPlaceReviewId(placeReviewId);
        return new PlaceReviewResponseDto(placeReview);
    }

    @Override
    public PlaceResponseDto deleteReview(Long placeReviewId, Long placeId) {
        placeReviewRepository.deleteById(placeReviewId);
        Place place = placeService.findByPlaceId(placeId);
        return new PlaceResponseDto(place);
    }

    private PlaceReview findByPlaceReviewId(Long placeReviewId) {
        return placeReviewRepository.findById(placeReviewId).orElseThrow(NotFoundException::new);
    }
}
