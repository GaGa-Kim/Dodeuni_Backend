package com.dodeuni.dodeuni.web.dto.place;

import com.dodeuni.dodeuni.domain.place.PlaceReview;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceReviewSaveRequestDto {
    @ApiModelProperty(notes = "추천 장소 아이디", dataType = "Long", example = "1")
    private Long placeId;

    @ApiModelProperty(notes = "추천 장소 후기 작성 회원 아이디", dataType = "Long", example = "1")
    private Long userId;

    @ApiModelProperty(notes = "추천 장소 후기 제목", dataType = "String", example = "제목")
    private String title;

    @ApiModelProperty(notes = "추천 장소 후기 내용", dataType = "String", example = "내용")
    private String content;

    @Builder
    public PlaceReviewSaveRequestDto(String title, String content, Long placeId, Long userId) {
        this.title = title;
        this.content = content;
        this.placeId = placeId;
        this.userId = userId;
    }

    public PlaceReview toEntity() {
        return PlaceReview.builder()
                .title(title)
                .content(content)
                .build();
    }
}
