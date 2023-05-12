package com.dodeuni.dodeuni.domain.place;

import com.dodeuni.dodeuni.domain.BaseTime;
import com.dodeuni.dodeuni.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class PlaceReview extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PlaceReviewId")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PlaceId")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    private User user;

    @Builder
    public PlaceReview(String title, String content){
        this.title=title;
        this.content=content;
    }

    public void setPlace(Place place){
        this.place = place;
        if(!place.getPlaceReviewList().contains(this))
            place.getPlaceReviewList().add(this);
    }

    public void setUser(User user) {
        this.user = user;
        if(!user.getPlaceReviewList().contains(this))
            user.getPlaceReviewList().add(this);
    }

}
