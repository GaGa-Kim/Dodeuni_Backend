package com.dodeuni.dodeuni.web.dto.place;

import com.dodeuni.dodeuni.domain.place.PlaceReview;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaceReviewSaveRequestDto {
    private String title;
    private String content;
    private Long pid;
    private Long uid;

    @Builder
    public PlaceReviewSaveRequestDto(String title, String content,
                                     Long pid, Long uid){
        this.title=title;
        this.content=content;
        this.pid=pid;
        this.uid=uid;
    }

    public PlaceReview toEntity(){
        return PlaceReview.builder()
                .title(title)
                .content(content)
                .build();
    }
}
