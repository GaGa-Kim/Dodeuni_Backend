package com.dodeuni.dodeuni.web.dto.user;

import com.dodeuni.dodeuni.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class UserResponseDto {
    @ApiModelProperty(notes = "회원 아이디", dataType = "Long", example = "1")
    private final Long userId;

    @ApiModelProperty(notes = "회원 이메일", dataType = "String", example = "dodeuni@gmail.com")
    private final String email;

    @ApiModelProperty(notes = "회원 닉네임", dataType = "String", example = "도드니")
    private final String nickname;

    public UserResponseDto(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}