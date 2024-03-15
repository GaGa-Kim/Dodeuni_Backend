package com.dodeuni.dodeuni.service.place;

import com.dodeuni.dodeuni.web.dto.place.PlaceResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceReviewResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceReviewSaveRequestDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PlaceReviewService {
    /**
     * 추천 장소에 후기를 저장한다.
     *
     * @param requestDto (추천 장소 저장 정보를 담은 DTO)
     * @return PlaceReviewResponseDto (추천 장소 후기 정보를 담은 DTO)
     */
    PlaceReviewResponseDto saveReview(PlaceReviewSaveRequestDto requestDto);

    /**
     * 추천 장소의 후기를 조회한다.
     *
     * @param placeReviewId (추천 장소의 후기 아이디)
     * @return PlaceReviewResponseDto (추천 장소 후기 정보를 담은 DTO)
     */
    @Transactional(readOnly = true)
    PlaceReviewResponseDto getPlaceReview(Long placeReviewId);

    /**
     * 추천 장소의 후기를 삭제한다.
     *
     * @param placeReviewId (추천 장소의 후기 아이디)
     * @param placeId       (추천 장소 아이디)
     * @return PlaceResponseDto (추천 장소 정보를 담은 DTO)
     */
    PlaceResponseDto deleteReview(Long placeReviewId, Long placeId);
}
