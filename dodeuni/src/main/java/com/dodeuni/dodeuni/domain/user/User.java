package com.dodeuni.dodeuni.domain.user;

import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.domain.place.Place;
import com.dodeuni.dodeuni.domain.place.PlaceReview;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    @ApiModelProperty(notes = "회원 아이디", dataType = "Long", example = "1")
    private Long id;

    @Column(length = 100, nullable = false)
    @ApiModelProperty(notes = "회원 이메일", dataType = "String", example = "dodeuni@gmail.com")
    private String email;

    @Column(length = 100, nullable = false)
    @ApiModelProperty(notes = "회원 닉네임", dataType = "String", example = "도드니")
    private String nickname;

    @ApiModelProperty(notes = "기기-사용자 별 fcm 토큰", dataType = "String", example = "A12D34")
    private String fcmToken;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ApiModelProperty(notes = "회원 권한", dataType = "Role", example = "일반 사용자")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Community> communityList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Place> placeList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<PlaceReview> placeReviewList = new ArrayList<>();

    @Builder
    public User(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.role = Role.USER;
    }

    public void updateProfile(String newNickname) {
        this.nickname = newNickname;
    }

    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}