package com.dodeuni.dodeuni.web.dto.hyu;

import com.dodeuni.dodeuni.domain.hyu.Hyu;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class HyuResponseDto {
    private Long id;
    private String content;
    private LocalDateTime createdDateTime;

    private Long uid;
    private String nickname; // 필요 없으면 제거

    public HyuResponseDto(Hyu hyu){
        this.id=hyu.getId();
        this.content=hyu.getContent();
        this.createdDateTime=hyu.getCreatedDateTime();
        this.uid=hyu.getUser().getId();
        this.nickname=hyu.getUser().getNickname(); // 필요 없으면 제거
    }
}
