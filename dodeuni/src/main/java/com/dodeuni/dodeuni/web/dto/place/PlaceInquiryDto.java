package com.dodeuni.dodeuni.web.dto.place;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceInquiryDto {
    @ApiModelProperty(notes = "회원 경도", dataType = "double", example = "126.96471647833378")
    @Min(0)
    private double x;

    @ApiModelProperty(notes = "회원 위도", dataType = "double", example = "37.54710883987478")
    @Min(0)
    private double y;

    @ApiModelProperty(notes = "검색 지역", dataType = "String", example = "서울특별시")
    @NotEmpty
    private String keyword;

    @Builder
    public PlaceInquiryDto(double x, double y, String keyword) {
        this.x = x;
        this.y = y;
        this.keyword = keyword;
    }
}
