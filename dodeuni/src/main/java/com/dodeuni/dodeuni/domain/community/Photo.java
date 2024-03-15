package com.dodeuni.dodeuni.domain.community;

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
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PhotoId")
    @ApiModelProperty(notes = "사진 아이디", dataType = "Long", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CommunityId")
    @ApiModelProperty(notes = "사진의 커뮤니티 게시글", dataType = "Community")
    private Community community;

    @Column(nullable = false)
    @ApiModelProperty(notes = "사진 원본 이름", dataType = "String", example = "원본 이름")
    private String origPhotoName;

    @Column(nullable = false)
    @ApiModelProperty(notes = "변환된 사진 이름", dataType = "String", example = "변환 이름")
    private String photoName;

    @Column(nullable = false)
    @ApiModelProperty(notes = "사진 저장 주소", dataType = "String")
    private String photoUrl;

    @Builder
    public Photo(Long id, String origPhotoName, String photoName, String photoUrl) {
        this.id = id;
        this.origPhotoName = origPhotoName;
        this.photoName = photoName;
        this.photoUrl = photoUrl;
    }

    public void setCommunity(Community community) {
        this.community = community;
        if (!community.getPhotoList().contains(this)) {
            community.getPhotoList().add(this);
        }
    }
}