package com.dodeuni.dodeuni.web.dto.hyu;

import com.dodeuni.dodeuni.domain.hyu.Hyu;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class HyuSaveRequestDto {

    private String content;
    private Long uid;

    @Builder
    public HyuSaveRequestDto(String content, Long uid){
        this.content=content;
        this.uid=uid;
    }

    public Hyu toEntity(){
        return Hyu.builder()
                .content(content)
                .build();
    }
}
