package com.dodeuni.dodeuni.service;

import com.dodeuni.dodeuni.domain.place.Place;
import com.dodeuni.dodeuni.domain.place.PlaceRepository;
import com.dodeuni.dodeuni.domain.place.PlaceReview;
import com.dodeuni.dodeuni.domain.place.PlaceReviewRepository;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserRepository;
import com.dodeuni.dodeuni.web.dto.place.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PlaceService {
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    private final PlaceReviewRepository reviewRepository;

    /* 추천 장소 등록 */
    @Transactional
    public PlaceResponseDto savePlace(PlaceSaveRequestDto requestDto){

        Place place = placeRepository.findByNameAndAddressAndXAndY(
                requestDto.getName(), requestDto.getAddress(), requestDto.getX(), requestDto.getY());

        if (place!=null){ // 기존에 등록된 장소면 해당 장소의 데이터 리턴
            return new PlaceResponseDto(place);
        }else{ // 새로 추가되는 장소면 db에 해당 장소 데이터 저장
            User user = userRepository.findById(requestDto.getUid())
                    .orElseThrow(IllegalArgumentException::new);

            Place newPlace = requestDto.toEntity();
            newPlace.setUser(user);
            placeRepository.save(newPlace);
            return new PlaceResponseDto(newPlace);
        }
    }

    /* 추천 장소 리스트 조회 */
    @Transactional(readOnly = true)
    public List<PlaceListResponseDto> getPlaceList(PlaceInquiryDto placeInquiryDto){
        return placeRepository.getPlaceByLocationAndKeyword(placeInquiryDto.getX(),placeInquiryDto.getY(), placeInquiryDto.getKeyword());
    }

    /* 추천 장소 상세 조회 */
    @Transactional(readOnly = true)
    public PlaceResponseDto findById(Long id){
        Place place = placeRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return new PlaceResponseDto(place);
    }


    /* 리뷰 등록 */
    @Transactional
    public PlaceReviewResponseDto saveReview(PlaceReviewSaveRequestDto requestDto){
        Place place = placeRepository.findById(requestDto.getPid())
                .orElseThrow(IllegalArgumentException::new);

        User user = userRepository.findById(requestDto.getUid())
                .orElseThrow(IllegalArgumentException::new);

        PlaceReview placeReview = requestDto.toEntity();
        placeReview.setPlace(place);
        placeReview.setUser(user);
        reviewRepository.save(placeReview);
        return new PlaceReviewResponseDto(placeReview);
    }

    /* 리뷰 상세 조회 */
    @Transactional(readOnly = true)
    public PlaceReviewResponseDto reviewFindById(Long id){
        PlaceReview placeReview = reviewRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
        return new PlaceReviewResponseDto(placeReview);
    }

    /* 리뷰 삭제 */
    @Transactional
    public PlaceResponseDto deleteReview(Long id, Long pid){
        PlaceReview placeReview = reviewRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        Place place = placeRepository.findById(pid)
                .orElseThrow(IllegalArgumentException::new);

        place.getPlaceReviewList().removeIf(review -> review.getId().equals(id));
        reviewRepository.deleteById(placeReview.getId());


        return new PlaceResponseDto(place);
    }


}
