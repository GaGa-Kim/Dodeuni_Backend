package com.dodeuni.dodeuni.domain.place;

import com.dodeuni.dodeuni.domain.BaseTime;
import com.dodeuni.dodeuni.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Place extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="PlaceId")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private String address;

    private String contact;

    @Column(nullable = false)
    private double x;           // 경도

    @Column(nullable = false)
    private double y;           // 위도

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    private User user;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PlaceReview> placeReviewList = new ArrayList<>();

    @Builder
    public Place(String name, String category, String address, String contact,
                 double x, double y){
        this.name=name;
        this.category=category;
        this.address=address;
        this.contact=contact;
        this.x=x;
        this.y=y;
    }

    public void setUser(User user) {
        this.user = user;
        if(!user.getPlaceList().contains(this))
            user.getPlaceList().add(this);
    }
}
