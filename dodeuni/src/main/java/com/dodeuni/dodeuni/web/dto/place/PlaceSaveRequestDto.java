package com.dodeuni.dodeuni.web.dto.place;

import com.dodeuni.dodeuni.domain.place.Place;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceSaveRequestDto {
    @ApiModelProperty(notes = "회원 아이디", dataType = "Long", example = "1")
    private Long userId;

    @ApiModelProperty(notes = "추천 장소 이름", dataType = "String", example = "이름")
    private String name;

    @ApiModelProperty(notes = "추천 장소 카테고리", dataType = "String", example = "사용자 추천")
    private String category;

    @ApiModelProperty(notes = "추천 장소 주소", dataType = "String", example = "주소")
    private String address;

    @ApiModelProperty(notes = "추천 장소 연락처", dataType = "String", example = "연락처")
    private String contact;

    @ApiModelProperty(notes = "추천 장소 경도", dataType = "double", example = "126.96471647833378")
    private double x;

    @ApiModelProperty(notes = "추천 장소 위도", dataType = "double", example = "37.54710883987478")
    private double y;

    @Builder
    public PlaceSaveRequestDto(String name, String category, String address, String contact,
                               double x, double y, Long userId) {
        this.name = name;
        this.category = category;
        this.address = address;
        this.contact = contact;
        this.x = x;
        this.y = y;
        this.userId = userId;
    }

    public Place toEntity() {
        return Place.builder()
                .name(name)
                .category(category)
                .address(address)
                .contact(contact)
                .x(x)
                .y(y)
                .build();
    }
}
