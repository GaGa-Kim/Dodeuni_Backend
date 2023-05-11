package com.dodeuni.dodeuni.web.dto.alarm;

import com.dodeuni.dodeuni.domain.Time;
import com.dodeuni.dodeuni.domain.comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AlarmResponseDto {

    private Long id;

    private String main;

    private String sub;

    private String createdDateTime;

    private String alarm;

    public AlarmResponseDto(Comment comment) {
        this.id = comment.getCommunity().getId();
        this.main = comment.getCommunity().getMain();
        this.sub = comment.getCommunity().getSub();
        this.createdDateTime = Time.calculateTime(java.sql.Timestamp.valueOf(comment.getCreatedDateTime()));
        this.alarm = comment.getUser().getNickname()+"님이 회원님의 게시글에 댓글을 달았습니다.";
    }
}
