package com.dodeuni.dodeuni.web.dto.auth;

import com.dodeuni.dodeuni.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class TokenResponseDto {
    @ApiModelProperty(notes = "회원 아이디", dataType = "Long", example = "1")
    private final Long userId;

    @ApiModelProperty(notes = "회원 이메일", dataType = "String", example = "dodeuni@gmail.com")
    private final String email;

    @ApiModelProperty(notes = "회원 닉네임", dataType = "String", example = "도드니")
    private final String nickname;

    @ApiModelProperty(notes = "JWT 토큰", dataType = "String", example = "token")
    private final String token;

    public TokenResponseDto(User user, String token) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.token = token;
    }
}