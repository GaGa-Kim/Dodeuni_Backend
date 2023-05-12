package com.dodeuni.dodeuni.web.dto.place;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaceInquiryDto {
    private double x;           // 사용자 경도
    private double y;           // 사용자 위도
    private String keyword;     // 검색어(지역)

    @Builder
    public PlaceInquiryDto(double x, double y, String keyword){
        this.x=x;
        this.y=y;
        this.keyword=keyword;
    }
}
