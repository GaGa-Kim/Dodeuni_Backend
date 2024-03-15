package com.dodeuni.dodeuni.web.dto.auth;

import com.dodeuni.dodeuni.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSaveRequestDto {
    @ApiModelProperty(notes = "회원 이메일", dataType = "String", example = "dodeuni@gmail.com")
    @Email
    private String email;

    @ApiModelProperty(notes = "회원 닉네임", dataType = "String", example = "도드니")
    @NotEmpty
    private String nickname;

    @Builder
    public UserSaveRequestDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    public User toEntity() {
        return User.builder()
                .email(email)
                .nickname(nickname)
                .build();
    }
}