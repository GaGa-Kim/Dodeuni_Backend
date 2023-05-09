package com.dodeuni.dodeuni.web.dto.community;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommunityUpdateRequestDto {

    private Long id;

    private Long userId;

    private String main;

    private String sub;

    private String title;

    private String content;
    
    // TODO : 추가할 사진, 삭제할 사진 추가

    @Builder
    public CommunityUpdateRequestDto(Long id, Long userId, String main, String sub, String title, String content) {
        this.id = id;
        this.userId = userId;
        this.main = main;
        this.sub = sub;
        this.title = title;
        this.content = content;
    }
}
