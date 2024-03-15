package com.dodeuni.dodeuni.web.dto.place;

import com.dodeuni.dodeuni.domain.place.PlaceReview;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PlaceReviewResponseDto {
    @ApiModelProperty(notes = "추천 장소 후기 아이디", dataType = "Long", example = "1")
    private final Long placeReviewId;

    @ApiModelProperty(notes = "추천 장소 후기 작성 회원 아이디", dataType = "Long", example = "1")
    private final Long userId;

    @ApiModelProperty(notes = "추천 장소 후기 작성 회원 닉네임", dataType = "String", example = "도드니")
    private final String nickname;

    @ApiModelProperty(notes = "추천 장소 아이디", dataType = "Long", example = "1")
    private final Long placeId;

    @ApiModelProperty(notes = "추천 장소 후기 제목", dataType = "String", example = "제목")
    private final String title;
    @ApiModelProperty(notes = "추천 장소 후기 내용", dataType = "String", example = "내용")

    private final String content;

    @ApiModelProperty(notes = "생성 날짜", dataType = "LocalDateTime", example = "20XX-11-XXT11:44:30.327959")
    private final LocalDateTime createdDateTime;

    @ApiModelProperty(notes = "수정 날짜", dataType = "LocalDateTime", example = "20XX-XX-XXT11:44:30.327959")
    private final LocalDateTime modifiedDateTime;

    public PlaceReviewResponseDto(PlaceReview placeReview) {
        this.placeReviewId = placeReview.getId();
        this.title = placeReview.getTitle();
        this.content = placeReview.getContent();
        this.createdDateTime = placeReview.getCreatedDateTime();
        this.modifiedDateTime = placeReview.getModifiedDateTime();
        this.placeId = placeReview.getPlace().getId();
        this.userId = placeReview.getUser().getId();
        this.nickname = placeReview.getUser().getNickname();
    }
}
