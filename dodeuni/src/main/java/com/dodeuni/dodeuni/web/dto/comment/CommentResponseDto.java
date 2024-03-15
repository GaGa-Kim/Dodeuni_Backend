package com.dodeuni.dodeuni.web.dto.comment;

import com.dodeuni.dodeuni.domain.comment.Comment;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    @ApiModelProperty(notes = "댓글 아이디", dataType = "Long", example = "1")
    private final Long commentId;

    @ApiModelProperty(notes = "댓글 작성 회원 아이디", dataType = "Long", example = "1")
    private final Long userId;

    @ApiModelProperty(notes = "댓글 작성 회원 이메일", dataType = "String", example = "dodeuni@gmail.com")
    private final String nickname;

    @ApiModelProperty(notes = "커뮤니티 게시글 아이디", dataType = "Long", example = "1")
    private final Long communityId;

    @ApiModelProperty(notes = "댓글 내용", dataType = "String", example = "내용")
    private final String content;

    @ApiModelProperty(notes = "댓글 계층 (댓글일 경우 0, 답댓글일 경우 1)", dataType = "Long", example = "1")
    private final Long step;

    @ApiModelProperty(notes = "부모 댓글 아이디 ", dataType = "Long", example = "1")
    private final Long pid;

    @ApiModelProperty(notes = "생성 날짜", dataType = "LocalDateTime", example = "20XX-11-XXT11:44:30.327959")
    private final LocalDateTime createdDateTime;
    
    @ApiModelProperty(notes = "수정 날짜", dataType = "LocalDateTime", example = "20XX-XX-XXT11:44:30.327959")
    private final LocalDateTime modifiedDateTime;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.step = comment.getStep();
        this.pid = comment.getPid();
        this.modifiedDateTime = comment.getModifiedDateTime();
        this.createdDateTime = comment.getCreatedDateTime();
        this.communityId = comment.getCommunity().getId();
        this.userId = comment.getUser().getId();
        this.nickname = comment.getUser().getNickname();
    }
}
