package com.dodeuni.dodeuni.web.dto.hyu;

import com.dodeuni.dodeuni.domain.hyu.Hyu;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HyuSaveRequestDto {
    @ApiModelProperty(notes = "휴 작성 회원 아이디", dataType = "Long", example = "1")
    private Long userId;

    @ApiModelProperty(notes = "휴 내용", dataType = "String", example = "내용")
    private String content;

    @Builder
    public HyuSaveRequestDto(Long userId, String content) {
        this.userId = userId;
        this.content = content;
    }

    public Hyu toEntity() {
        return Hyu.builder()
                .content(content)
                .build();
    }
}
