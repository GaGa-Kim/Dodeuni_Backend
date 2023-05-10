package com.dodeuni.dodeuni.web.dto.community;

import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.domain.community.Photo;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CommunityResponseDto {

    private Long id;

    private LocalDateTime createdDateTime;

    private Long userId;

    private String email;

    private String nickname;

    private String main;

    private String sub;

    private String title;

    private String content;

    private List<Long> photoId;

    private List<String> photoUrl;

    public CommunityResponseDto(Community community) {
        this.id = community.getId();
        this.createdDateTime = community.getCreatedDateTime();
        this.userId = community.getUserId().getId();
        this.email = community.getUserId().getEmail();
        this.nickname = community.getUserId().getNickname();
        this.main = community.getMain();
        this.sub = community.getSub();
        this.title = community.getTitle();
        this.content = community.getContent();
        if (!community.getPhotoList().isEmpty()) {
            this.photoId = community.getPhotoList().stream().map(Photo::getId).collect(Collectors.toList());
            this.photoUrl = community.getPhotoList().stream().map(Photo::getPhotoUrl).collect(Collectors.toList());
        }
        else {
            this.photoId = null;
            this.photoUrl = null;
        }
    }
}
