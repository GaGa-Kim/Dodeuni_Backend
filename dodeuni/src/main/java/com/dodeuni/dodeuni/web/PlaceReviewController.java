package com.dodeuni.dodeuni.web;

import com.dodeuni.dodeuni.service.place.PlaceReviewService;
import com.dodeuni.dodeuni.web.dto.place.PlaceResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceReviewResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceReviewSaveRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"PlaceReview API (추천 장소 후기 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/places/reviews")
public class PlaceReviewController {
    private final PlaceReviewService placeReviewService;

    @PostMapping
    @ApiOperation(notes = "추천 장소에 리뷰를 저장", value = "추천 장소 후기 저장 API")
    public ResponseEntity<PlaceReviewResponseDto> save(@RequestBody @Valid PlaceReviewSaveRequestDto requestDto) {
        return ResponseEntity.ok().body(placeReviewService.saveReview(requestDto));
    }

    @GetMapping("/{placeReviewId}")
    @ApiOperation(notes = "추천 장소의 후기를 조회", value = "추천 장소 후기 상세 조회 API")
    @ApiImplicitParam(name = "placeReviewId", value = "추천 장소 후기 아이디", dataType = "Long", example = "1")
    public ResponseEntity<PlaceReviewResponseDto> view(@PathVariable @NotNull Long placeReviewId) {
        return ResponseEntity.ok().body(placeReviewService.getPlaceReview(placeReviewId));
    }

    @DeleteMapping
    @ApiOperation(notes = "추천 장소의 후기를 삭제", value = "추천 장소 후기 삭제 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "placeReviewId", value = "추천 장소 후기 아이디", dataType = "Long", example = "1"),
            @ApiImplicitParam(name = "placeId", value = "추천 장소 아이디", dataType = "Long", example = "1")
    })
    public ResponseEntity<PlaceResponseDto> delete(@RequestParam @NotNull Long placeReviewId,
                                                   @RequestParam @NotNull Long placeId) {
        return ResponseEntity.ok().body(placeReviewService.deleteReview(placeReviewId, placeId));
    }
}
