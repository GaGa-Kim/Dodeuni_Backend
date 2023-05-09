package com.dodeuni.dodeuni.web.dto.community;

import com.dodeuni.dodeuni.domain.community.Community;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommunitySaveRequestDto {

    private Long userId;

    private String main;

    private String sub;

    private String title;

    private String content;

    // TODO : 사진 추가

    @Builder
    public CommunitySaveRequestDto(Long userId, String main, String sub, String title, String content) {
        this.userId = userId;
        this.main = main;
        this.sub = sub;
        this.title = title;
        this.content = content;
    }

    public Community toEntity() {
        return Community.builder()
                .main(main)
                .sub(sub)
                .title(title)
                .content(content)
                .build();
    }
}
