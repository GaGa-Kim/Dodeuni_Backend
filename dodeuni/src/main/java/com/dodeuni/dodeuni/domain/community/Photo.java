package com.dodeuni.dodeuni.domain.community;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Photo_Id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Community_Id")
    private Community communityId;

    @Column(nullable = false)
    private String origPhotoName;

    @Column(nullable = false)
    private String photoName;

    @Column(nullable = false)
    private String photoUrl;

    @Builder
    public Photo(String origPhotoName, String photoName, String photoUrl) {
        this.origPhotoName = origPhotoName;
        this.photoName = photoName;
        this.photoUrl = photoUrl;
    }

    public void setCommunity(Community community) {
        this.communityId = community;
        if(!community.getPhotoList().contains(this))
            community.getPhotoList().add(this);
    }
}