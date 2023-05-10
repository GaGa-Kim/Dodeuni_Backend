package com.dodeuni.dodeuni.web.dto.comment;

import com.dodeuni.dodeuni.domain.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String content;
    private Long step;
    private Long pid;
    private LocalDateTime modifiedDateTime;
    private LocalDateTime createdDateTime;

    private Long cid;
    private Long uid;
    private String nickname;

    public CommentResponseDto(Comment comment){
        this.id=comment.getId();
        this.content=comment.getContent();
        this.step=comment.getStep();
        this.pid=comment.getPid();
        this.modifiedDateTime=comment.getModifiedDateTime();
        this.createdDateTime=comment.getCreatedDateTime();
        this.cid=comment.getCommunity().getId();
        this.uid=comment.getUser().getId();
        this.nickname=comment.getUser().getNickname();
    }
}
