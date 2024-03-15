package com.dodeuni.dodeuni.web;

import com.dodeuni.dodeuni.service.place.PlaceService;
import com.dodeuni.dodeuni.web.dto.place.PlaceInquiryDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceListResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceResponseDto;
import com.dodeuni.dodeuni.web.dto.place.PlaceSaveRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Place API (추천 장소 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/places")
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping
    @ApiOperation(notes = "추천 장소를 저장", value = "추천 장소 저장 API")
    @ApiImplicitParam(name = "requestDto", value = "추천 장소 저장 정보를 담은 DTO")
    public ResponseEntity<PlaceResponseDto> save(@RequestBody @Valid PlaceSaveRequestDto requestDto) {
        return ResponseEntity.ok().body(placeService.savePlace(requestDto));
    }

    @PostMapping("/list")
    @ApiOperation(notes = "추천 장소 목록을 조회", value = "추천 장소 목록 조회 API")
    @ApiImplicitParam(name = "placeInquiryDto", value = "추천 장소 질의 정보를 담은 DTO")
    public ResponseEntity<List<PlaceListResponseDto>> list(@RequestBody @Valid PlaceInquiryDto placeInquiryDto) {
        return ResponseEntity.ok().body(placeService.getPlaceList(placeInquiryDto));
    }

    @GetMapping("/{placeId}")
    @ApiOperation(notes = "추천 장소를 상세 조회", value = "추천 장소 상세 조회 API")
    @ApiImplicitParam(name = "placeId", value = "추천 장소 아이디", dataType = "Long", example = "1")
    public ResponseEntity<PlaceResponseDto> view(@PathVariable @NotNull Long placeId) {
        return ResponseEntity.ok().body(placeService.getPlace(placeId));
    }
}