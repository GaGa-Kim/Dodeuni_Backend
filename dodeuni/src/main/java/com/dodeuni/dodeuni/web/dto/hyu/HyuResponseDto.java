package com.dodeuni.dodeuni.web.dto.hyu;

import com.dodeuni.dodeuni.domain.hyu.Hyu;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class HyuResponseDto {
    @ApiModelProperty(notes = "휴 아이디", dataType = "Long", example = "1")
    private final Long hyuId;

    @ApiModelProperty(notes = "휴 작성 회원 아이디", dataType = "Long", example = "1")
    private final Long userId;

    @ApiModelProperty(notes = "휴 내용", dataType = "String", example = "내용")
    private final String content;

    @ApiModelProperty(notes = "생성 날짜", dataType = "LocalDateTime", example = "20XX-11-XXT11:44:30.327959")
    private final LocalDateTime createdDateTime;

    public HyuResponseDto(Hyu hyu) {
        this.hyuId = hyu.getId();
        this.userId = hyu.getUser().getId();
        this.content = hyu.getContent();
        this.createdDateTime = hyu.getCreatedDateTime();
    }
}