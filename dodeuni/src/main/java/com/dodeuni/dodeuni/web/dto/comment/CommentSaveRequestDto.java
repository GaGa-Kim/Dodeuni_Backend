package com.dodeuni.dodeuni.web.dto.comment;

import com.dodeuni.dodeuni.domain.comment.Comment;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentSaveRequestDto {
    private String content;
    private Long step;
    private Long pid;
    private Long cid;
    private Long uid;

    @Builder
    public CommentSaveRequestDto(String content, Long step,
                                 Long pid, Long cid, Long uid){
        this.content=content;
        this.step=step;
        this.pid=pid;
        this.cid=cid;
        this.uid=uid;
    }

    public Comment toEntity(){
        return Comment.builder()
                .content(content)
                .step(step)
                .pid(pid)
                .build();
    }
}
