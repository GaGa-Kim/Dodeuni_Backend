package com.dodeuni.dodeuni.web.dto.place;

import com.dodeuni.dodeuni.domain.place.Category;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PlaceListResponseDto {
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

    @ApiModelProperty(notes = "추천 장소와의 거리", dataType = "double", example = "30")
    private final double distance;

    @ApiModelProperty(notes = "추천 장소 후기 개수", dataType = "Long", example = "1")
    private final Long reviewsCount;

    @ApiModelProperty(notes = "생성 날짜", dataType = "LocalDateTime", example = "20XX-11-XXT11:44:30.327959")
    private final LocalDateTime createdDateTime;

    @ApiModelProperty(notes = "수정 날짜", dataType = "LocalDateTime", example = "20XX-XX-XXT11:44:30.327959")
    private final LocalDateTime modifiedDateTime;

    public PlaceListResponseDto(Long id, String name, Category category, String address, String contact,
                                double x, double y,
                                LocalDateTime createdDateTime, LocalDateTime modifiedDateTime,
                                Long uid, String nickname, double distance, Long reviewsCount) {
        this.placeId = id;
        this.userId = uid;
        this.nickname = nickname;
        this.name = name;
        this.category = category.getCategoryName();
        this.address = address;
        this.contact = contact;
        this.x = x;
        this.y = y;
        this.distance = distance;
        this.reviewsCount = reviewsCount;
        this.createdDateTime = createdDateTime;
        this.modifiedDateTime = modifiedDateTime;
    }
}
