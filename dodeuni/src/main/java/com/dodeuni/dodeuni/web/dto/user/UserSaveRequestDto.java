package com.dodeuni.dodeuni.web.dto.user;

import com.dodeuni.dodeuni.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveRequestDto {

    private String email;

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