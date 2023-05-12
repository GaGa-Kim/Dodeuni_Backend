package com.dodeuni.dodeuni.web;

import com.dodeuni.dodeuni.service.PlaceService;
import com.dodeuni.dodeuni.web.dto.place.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PlaceController {

    private final PlaceService placeService;

    /* 추천 장소 등록 */
    @PostMapping("/api/places")
    public PlaceResponseDto savePlace(@RequestBody PlaceSaveRequestDto requestDto){
        return placeService.savePlace(requestDto);
    }

    /* 추천 장소 리스트 조회 */
    @PostMapping("/api/places/list")
    public List<PlaceListResponseDto> getPlaceList(@RequestBody PlaceInquiryDto placeInquiryDto){
        return placeService.getPlaceList(placeInquiryDto);
    }

    /* 추천 장소 상세 조회 */
    @GetMapping("/api/places/{id}")
    public PlaceResponseDto findById(@PathVariable("id") Long id)throws Exception{
        return placeService.findById(id);
    }

    /* 리뷰 등록 */
    @PostMapping("/api/places/reviews")
    public PlaceReviewResponseDto saveReview(@RequestBody PlaceReviewSaveRequestDto requestDto){
        return placeService.saveReview(requestDto);
    }

    /* 리뷰 상세 조회 */
    @GetMapping("/api/places/reviews/{id}")
    public PlaceReviewResponseDto reviewFindById(@PathVariable("id") Long id)throws Exception{
        return placeService.reviewFindById(id);
    }

    /* 리뷰 삭제 */
    @DeleteMapping("/api/places/reviews/{id}/{pid}")
    public PlaceResponseDto deleteReview(
            @PathVariable("id") Long id,
            @PathVariable("pid") Long pid
    ){
        return placeService.deleteReview(id, pid);
    }

}
