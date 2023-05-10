package com.dodeuni.dodeuni.web.dto.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {
    private Long id;
    private String content;
    private Long cid;

    @Builder
    public CommentUpdateRequestDto(Long id, String content, Long cid){
        this.id=id;
        this.content=content;
        this.cid=cid;
    }
}
