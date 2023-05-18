package com.dodeuni.dodeuni.web.dto.place;

import com.dodeuni.dodeuni.domain.place.Place;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaceSaveRequestDto {
    private String name;
    private String category;
    private String address;
    private String contact;
    private double x;
    private double y;
    private Long uid;

    @Builder
    public PlaceSaveRequestDto(String name, String category, String address, String contact,
                               double x, double y, Long uid){
        this.name=name;
        this.category=category;
        this.address=address;
        this.contact=contact;
        this.x=x;
        this.y=y;
        this.uid=uid;
    }

    public Place toEntity(){
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
