package com.dodeuni.dodeuni.web.dto.place;

import com.dodeuni.dodeuni.domain.place.Place;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PlaceResponseDto {
    private Long id;
    private String name;
    private String category;
    private String address;
    private String contact;
    private double x;
    private double y;
    private LocalDateTime createdDateTime;
    private LocalDateTime modifiedDateTime;

    private Long uid;
    private String nickname;

    private List<PlaceReviewResponseDto> reviews;

    public PlaceResponseDto(Place place){
        this.id=place.getId();
        this.name=place.getName();
        this.category= place.getCategory();
        this.address=place.getAddress();
        this.contact=place.getContact();
        this.x=place.getX();
        this.y=place.getY();
        this.createdDateTime=place.getCreatedDateTime();
        this.modifiedDateTime=place.getModifiedDateTime();

        this.uid=place.getUser().getId();
        this.nickname=place.getUser().getNickname();

        Comparator<PlaceReviewResponseDto> sortedReviewList
                = Comparator.comparing(PlaceReviewResponseDto::getCreatedDateTime, Comparator.reverseOrder());
        this.reviews=place.getPlaceReviewList()
                .stream()
                .map(PlaceReviewResponseDto::new)
                .sorted(sortedReviewList)
                .collect(Collectors.toList());
    }
}
