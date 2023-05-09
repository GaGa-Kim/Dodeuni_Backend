package com.dodeuni.dodeuni.web.dto.community;

import com.dodeuni.dodeuni.domain.community.Community;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Date;

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

    // TODO : 사진 아이디, 사진 url 추가

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
    }
}
