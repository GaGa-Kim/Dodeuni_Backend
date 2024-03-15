package com.dodeuni.dodeuni.web.dto.community;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommunityUpdateRequestDto {
    @ApiModelProperty(notes = "커뮤니티 게시글 아이디", dataType = "Long", example = "1")
    @NotNull
    private Long communityId;

    @ApiModelProperty(notes = "커뮤니티 게시글 작성 회원 아이디", dataType = "Long", example = "1")
    @NotNull
    private Long userId;

    @ApiModelProperty(notes = "커뮤니티 게시글 카테고리 대분류", dataType = "String", example = "정보")
    @NotEmpty
    private String main;

    @ApiModelProperty(notes = "커뮤니티 게시글 카테고리 소분류", dataType = "String", example = "정보교환")
    @NotEmpty
    private String sub;

    @ApiModelProperty(notes = "커뮤니티 게시글 제목", dataType = "String", example = "제목")
    @NotEmpty
    private String title;

    @ApiModelProperty(notes = "커뮤니티 게시글 내용", dataType = "String", example = "내용")
    @NotEmpty
    private String content;

    @ApiModelProperty(notes = "추가할 커뮤니티 게시글 사진 목록", dataType = "List<MultipartFile>")
    private List<MultipartFile> addPhoto;

    @ApiModelProperty(notes = "삭제할 커뮤니티 게시글 사진 아이디 목록", dataType = "List<Long>", example = "[1, 2, 3]")
    private List<Long> deletePhotoId;

    @Builder
    public CommunityUpdateRequestDto(Long communityId, Long userId, String main, String sub, String title, String content, List<MultipartFile> addPhoto, List<Long> deletePhotoId) {
        this.communityId = communityId;
        this.userId = userId;
        this.main = main;
        this.sub = sub;
        this.title = title;
        this.content = content;
        this.addPhoto = addPhoto;
        this.deletePhotoId = deletePhotoId;
    }
}
