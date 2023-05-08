package com.dodeuni.dodeuni.web.dto.user;

import com.dodeuni.dodeuni.domain.user.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private Long id;

    private String email;

    private String nickname;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
    }
}