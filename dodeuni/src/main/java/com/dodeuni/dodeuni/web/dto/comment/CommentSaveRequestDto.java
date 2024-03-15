package com.dodeuni.dodeuni.web.dto.comment;

import com.dodeuni.dodeuni.domain.comment.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentSaveRequestDto {
    @ApiModelProperty(notes = "댓글 작성 회원 아이디", dataType = "Long", example = "1")
    private Long userId;

    @ApiModelProperty(notes = "커뮤니티 게시글 아이디", dataType = "Long", example = "1")
    private Long communityId;

    @ApiModelProperty(notes = "댓글 내용", dataType = "String", example = "내용")
    private String content;

    @ApiModelProperty(notes = "댓글 계층 (댓글일 경우 0, 답댓글일 경우 1)", dataType = "Long", example = "1")
    private Long step;

    @ApiModelProperty(notes = "부모 댓글 아이디 ", dataType = "Long", example = "1")
    private Long pid;

    @Builder
    public CommentSaveRequestDto(Long userId, Long communityId, String content, Long step, Long pid) {
        this.userId = userId;
        this.communityId = communityId;
        this.content = content;
        this.step = step;
        this.pid = pid;
    }

    public Comment toEntity() {
        return Comment.builder()
                .content(content)
                .step(step)
                .pid(pid)
                .build();
    }
}
