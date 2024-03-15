package com.dodeuni.dodeuni.service.auth;

import com.dodeuni.dodeuni.web.dto.auth.TokenResponseDto;
import com.dodeuni.dodeuni.web.dto.auth.UserSaveRequestDto;
import javax.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AuthService {
    /**
     * 소셜 회원 가입 또는 로그인을 진행한 후 JWT 토큰과 회원 정보를 발급받는다.
     *
     * @param requestDto (회원 저장 정보를 담은 DTO)
     * @param response   (HttpServletResponse)
     * @return TokenResponseDto (JWT 토큰과 회원 정보를 담은 DTO)
     */
    TokenResponseDto registerOrLogin(UserSaveRequestDto requestDto, HttpServletResponse response);
}