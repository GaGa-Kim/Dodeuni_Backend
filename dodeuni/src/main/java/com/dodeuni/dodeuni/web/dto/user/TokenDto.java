package com.dodeuni.dodeuni.web.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenDto {

    private Long id;

    private String email;

    private String nickname;

    private String token;

    public TokenDto(Long id, String email, String nickname, String token) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.token = token;
    }
}