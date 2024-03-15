package com.dodeuni.dodeuni.domain.place;

import com.dodeuni.dodeuni.domain.BaseTime;
import com.dodeuni.dodeuni.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class PlaceReview extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlaceReviewId")
    @ApiModelProperty(notes = "추천 장소 후기 아이디", dataType = "Long", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId")
    @ApiModelProperty(notes = "추천 장소 후기 작성 회원", dataType = "User")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PlaceId")
    @ApiModelProperty(notes = "추천 장소", dataType = "Place")
    private Place place;

    @Column(nullable = false)
    @ApiModelProperty(notes = "추천 장소 후기 제목", dataType = "String", example = "제목")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @ApiModelProperty(notes = "추천 장소 후기 내용", dataType = "String", example = "내용")
    private String content;

    @Builder
    public PlaceReview(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public void setUser(User user) {
        this.user = user;
        if (!user.getPlaceReviewList().contains(this)) {
            user.getPlaceReviewList().add(this);
        }
    }

    public void setPlace(Place place) {
        this.place = place;
        if (!place.getPlaceReviewList().contains(this)) {
            place.getPlaceReviewList().add(this);
        }
    }
}
