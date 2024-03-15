package com.dodeuni.dodeuni.web.dto.place;

import com.dodeuni.dodeuni.domain.place.Place;
import com.dodeuni.dodeuni.domain.place.PlaceReview;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class PlaceResponseDto {
    @ApiModelProperty(notes = "추천 장소 아이디", dataType = "Long", example = "1")
    private final Long placeId;

    @ApiModelProperty(notes = "회원 아이디", dataType = "Long", example = "1")
    private final Long userId;

    @ApiModelProperty(notes = "회원 닉네임", dataType = "String", example = "도드니")
    private final String nickname;

    @ApiModelProperty(notes = "추천 장소 이름", dataType = "String", example = "이름")
    private final String name;

    @ApiModelProperty(notes = "추천 장소 카테고리", dataType = "String", example = "사용자 추천")
    private final String category;

    @ApiModelProperty(notes = "추천 장소 주소", dataType = "String", example = "주소")
    private final String address;

    @ApiModelProperty(notes = "추천 장소 연락처", dataType = "String", example = "연락처")
    private final String contact;

    @ApiModelProperty(notes = "추천 장소 경도", dataType = "double", example = "126.96471647833378")
    private final double x;

    @ApiModelProperty(notes = "추천 장소 위도", dataType = "double", example = "37.54710883987478")
    private final double y;

    @ApiModelProperty(notes = "추천 장소 후기 목록", dataType = "List<PlaceReviewResponseDto>")
    private List<PlaceReviewResponseDto> reviews;

    @ApiModelProperty(notes = "생성 날짜", dataType = "LocalDateTime", example = "20XX-11-XXT11:44:30.327959")
    private final LocalDateTime createdDateTime;

    @ApiModelProperty(notes = "수정 날짜", dataType = "LocalDateTime", example = "20XX-XX-XXT11:44:30.327959")
    private final LocalDateTime modifiedDateTime;

    public PlaceResponseDto(Place place) {
        this.placeId = place.getId();
        this.userId = place.getUser().getId();
        this.nickname = place.getUser().getNickname();
        this.name = place.getName();
        this.category = place.getCategory().getCategoryName();
        this.address = place.getAddress();
        this.contact = place.getContact();
        this.x = place.getX();
        this.y = place.getY();
        setReview(place.getPlaceReviewList());
        this.createdDateTime = place.getCreatedDateTime();
        this.modifiedDateTime = place.getModifiedDateTime();
    }

    private void setReview(List<PlaceReview> placeList) {
        Comparator<PlaceReviewResponseDto> sortedReviewList = Comparator.comparing(PlaceReviewResponseDto::getCreatedDateTime, Comparator.reverseOrder());
        this.reviews = placeList
                .stream()
                .map(PlaceReviewResponseDto::new)
                .sorted(sortedReviewList)
                .collect(Collectors.toList());
    }
}
