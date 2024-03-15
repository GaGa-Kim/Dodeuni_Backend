package com.dodeuni.dodeuni.service.place;

import com.dodeuni.dodeuni.domain.place.Place;
import com.dodeuni.dodeuni.web.dto.place.PlaceInquiryDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceListResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceSaveRequestDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface PlaceService {
    /**
     * 추천 장소를 저장한다.
     *
     * @param requestDto (추천 장소 저장 정보를 담은 DTO)
     * @return PlaceResponseDto (추천 장소 정보를 담은 DTO)
     */
    @Transactional
    PlaceResponseDto savePlace(PlaceSaveRequestDto requestDto);

    /**
     * 추천 장소 목록을 조회한다.
     *
     * @param placeInquiryDto (추천 장소 질의 정보를 담은 DTO)
     * @return List<PlaceListResponseDto> (추천 장소 목록 정보를 담은 DTO)
     */
    List<PlaceListResponseDto> getPlaceList(PlaceInquiryDto placeInquiryDto);

    /**
     * 추천 장소를 상세 조회한다.
     *
     * @param placeId (추천 장소 아이디)
     * @return Place (추천 장소)
     */
    Place findByPlaceId(Long placeId);

    /**
     * 추천 장소를 조회한다.
     *
     * @param placeId (추천 장소 아이디)
     * @return PlaceResponseDto (추천 장소 정보를 담은 DTO)
     */
    PlaceResponseDto getPlace(Long placeId);
}
