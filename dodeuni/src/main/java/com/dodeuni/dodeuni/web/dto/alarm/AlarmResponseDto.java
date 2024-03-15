package com.dodeuni.dodeuni.web.dto.alarm;

import com.dodeuni.dodeuni.domain.Time;
import com.dodeuni.dodeuni.domain.comment.Comment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class AlarmResponseDto {
    private static final String COMMENT_ALARM_MESSAGE = "님이 회원님의 게시글에 댓글을 달았습니다.";

    @ApiModelProperty(notes = "커뮤니티 게시글 아이디", dataType = "Long", example = "1")
    private final Long id;

    @ApiModelProperty(notes = "커뮤니티 게시글 카테고리 대분류", dataType = "String", example = "정보")
    private final String main;

    @ApiModelProperty(notes = "커뮤니티 게시글 카테고리 소분류", dataType = "String", example = "정보교환")
    private final String sub;

    @ApiModelProperty(notes = "알람 문구", dataType = "String", example = "도드니님" + COMMENT_ALARM_MESSAGE)
    private final String alarm;

    @ApiModelProperty(notes = "댓글 알람 생성 날짜", dataType = "String", example = "10분 전")
    private final String createdDateTime;

    public AlarmResponseDto(Comment comment) {
        this.id = comment.getCommunity().getId();
        this.main = comment.getCommunity().getMain().getMainName();
        this.sub = comment.getCommunity().getSub().getSubName();
        this.createdDateTime = Time.calculateTime(java.sql.Timestamp.valueOf(comment.getCreatedDateTime()));
        this.alarm = comment.getUser().getNickname() + COMMENT_ALARM_MESSAGE;
    }
}
