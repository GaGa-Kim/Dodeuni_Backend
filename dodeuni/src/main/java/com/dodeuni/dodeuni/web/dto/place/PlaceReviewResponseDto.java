package com.dodeuni.dodeuni.web.dto.place;

import com.dodeuni.dodeuni.domain.place.PlaceReview;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PlaceReviewResponseDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;

    private Long pid;
    private Long uid;
    private String nickname;

    public PlaceReviewResponseDto(PlaceReview placeReview){
        this.id=placeReview.getId();
        this.title=placeReview.getTitle();
        this.content= placeReview.getContent();
        this.createdDateTime=placeReview.getCreatedDateTime();
        this.modifiedDateTime=placeReview.getModifiedDateTime();
        this.pid=placeReview.getPlace().getId();
        this.uid=placeReview.getUser().getId();
        this.nickname=placeReview.getUser().getNickname();
    }
}
