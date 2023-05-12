package com.dodeuni.dodeuni.web.dto.place;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PlaceListResponseDto {
    private Long id;
    private String name;
    private String address;
    private String contact;
    private double x;
    private double y;
    private double distance;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;

    private Long uid;
    private String nickname;

    public PlaceListResponseDto(Long id, String name, String address, String contact,
                                double x, double y,
                                LocalDateTime createdDateTime, LocalDateTime modifiedDateTime,
                                Long uid, String nickname, double distance){
        this.id=id;
        this.name=name;
        this.address=address;
        this.contact=contact;
        this.x=x;
        this.y=y;
        this.distance=distance;
        this.createdDateTime=createdDateTime;
        this.modifiedDateTime=modifiedDateTime;
        this.uid=uid;
        this.nickname=nickname;

    }
}
