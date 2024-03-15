package com.dodeuni.dodeuni.web.dto.community;

import com.dodeuni.dodeuni.domain.community.Community;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommunityListResponseDto {
    @ApiModelProperty(notes = "커뮤니티 게시글 아이디", dataType = "Long", example = "1")
    private final Long communityId;

    @ApiModelProperty(notes = "커뮤니티 게시글 작성 회원 아이디", dataType = "Long", example = "1")
    private final Long userId;

    @ApiModelProperty(notes = "커뮤니티 게시글 작성 회원 아이디", dataType = "Long", example = "1")
    private final String email;

    @ApiModelProperty(notes = "커뮤니티 게시글 작성 회원 이메일", dataType = "String", example = "dodeuni@gmail.com")
    private final String nickname;

    @ApiModelProperty(notes = "커뮤니티 게시글 카테고리 대분류", dataType = "String", example = "정보")
    private final String main;

    @ApiModelProperty(notes = "커뮤니티 게시글 카테고리 소분류", dataType = "String", example = "정보교환")
    private final String sub;

    @ApiModelProperty(notes = "커뮤니티 게시글 제목", dataType = "String", example = "제목")
    private final String title;

    @ApiModelProperty(notes = "커뮤니티 게시글 내용", dataType = "String", example = "내용")
    private final String content;

    @ApiModelProperty(notes = "커뮤니티 게시글 썸네일 사진 아이디", dataType = "Long", example = "1")
    private Long thumbnailId;

    @ApiModelProperty(notes = "커뮤니티 게시글 썸네일 사진 URL", dataType = "String")
    private String thumbnailUrl;

    @ApiModelProperty(notes = "생성 날짜", dataType = "LocalDateTime", example = "20XX-11-XXT11:44:30.327959")
    private final LocalDateTime createdDateTime;

    public CommunityListResponseDto(Community community) {
        this.communityId = community.getId();
        this.createdDateTime = community.getCreatedDateTime();
        this.userId = community.getUser().getId();
        this.email = community.getUser().getEmail();
        this.nickname = community.getUser().getNickname();
        this.main = community.getMain().getMainName();
        this.sub = community.getSub().getSubName();
        this.title = community.getTitle();
        this.content = community.getContent();
        setThumbnail(community);
    }

    private void setThumbnail(Community community) {
        if (!community.getPhotoList().isEmpty()) {
            this.thumbnailId = community.getPhotoList().get(0).getId();
            this.thumbnailUrl = community.getPhotoList().get(0).getPhotoUrl();
        } else {
            this.thumbnailId = null;
            this.thumbnailUrl = null;
        }
    }
}
