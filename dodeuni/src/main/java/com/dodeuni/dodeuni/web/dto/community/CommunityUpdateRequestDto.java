package com.dodeuni.dodeuni.web.dto.community;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    private List<MultipartFile> addPhoto;

    private List<Long> deletePhotoId;

    @Builder
    public CommunityUpdateRequestDto(Long id, Long userId, String main, String sub, String title, String content, List<MultipartFile> addPhoto, List<Long> deletePhotoId) {
        this.id = id;
        this.userId = userId;
        this.main = main;
        this.sub = sub;
        this.title = title;
        this.content = content;
        this.addPhoto = addPhoto;
        this.deletePhotoId = deletePhotoId;
    }
}
