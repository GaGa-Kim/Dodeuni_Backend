package com.dodeuni.dodeuni.web.dto.community;

import com.dodeuni.dodeuni.domain.community.Community;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommunitySaveRequestDto {

    private Long userId;

    private String main;

    private String sub;

    private String title;

    private String content;

    private List<MultipartFile> photo;

    @Builder
    public CommunitySaveRequestDto(Long userId, String main, String sub, String title, String content, List<MultipartFile> photo) {
        this.userId = userId;
        this.main = main;
        this.sub = sub;
        this.title = title;
        this.content = content;
        this.photo = photo;
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
