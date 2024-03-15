package com.dodeuni.dodeuni.web.dto.user;

import com.dodeuni.dodeuni.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenResponseDto {
    @ApiModelProperty(notes = "회원 아이디", dataType = "Long", example = "1")
    private Long userId;

    @ApiModelProperty(notes = "회원 이메일", dataType = "String", example = "dodeuni@gmail.com")
    private String email;

    @ApiModelProperty(notes = "회원 닉네임", dataType = "String", example = "도드니")
    private String nickname;

    @ApiModelProperty(notes = "JWT 토큰", dataType = "String", example = "token")
    private String token;

    public TokenResponseDto(User user, String token) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.token = token;
    }
}