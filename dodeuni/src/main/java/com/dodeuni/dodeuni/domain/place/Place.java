package com.dodeuni.dodeuni.domain.place;

import com.dodeuni.dodeuni.domain.BaseTime;
import com.dodeuni.dodeuni.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Place extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PlaceId")
    @ApiModelProperty(notes = "추천 장소 아이디", dataType = "Long", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId")
    @ApiModelProperty(notes = "추천 장소 등록 회원", dataType = "User")
    private User user;

    @Column(nullable = false)
    @ApiModelProperty(notes = "추천 장소 이름", dataType = "String", example = "이름")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ApiModelProperty(notes = "추천 장소 카테고리", dataType = "Category", example = "사용자 추천")
    private Category category;

    @Column(nullable = false)
    @ApiModelProperty(notes = "추천 장소 주소", dataType = "String", example = "주소")
    private String address;

    @ApiModelProperty(notes = "추천 장소 연락처", dataType = "String", example = "연락처")
    private String contact;

    @Column(nullable = false)
    @ApiModelProperty(notes = "추천 장소 경도", dataType = "double", example = "126.96471647833378")
    private double x;

    @Column(nullable = false)
    @ApiModelProperty(notes = "추천 장소 위도", dataType = "double", example = "37.54710883987478")
    private double y;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL, orphanRemoval = true)
    @ApiModelProperty(notes = "추천 장소 후기 목록", dataType = "List<PlaceReview>")
    private final List<PlaceReview> placeReviewList = new ArrayList<>();

    @Builder
    public Place(Long id, String name, String category, String address, String contact,
                 double x, double y) {
        this.id = id;
        this.name = name;
        this.category = Category.findCategoryName(category);
        this.address = address;
        this.contact = contact;
        this.x = x;
        this.y = y;
    }

    public void setUser(User user) {
        this.user = user;
        if (!user.getPlaceList().contains(this)) {
            user.getPlaceList().add(this);
        }
    }
}
