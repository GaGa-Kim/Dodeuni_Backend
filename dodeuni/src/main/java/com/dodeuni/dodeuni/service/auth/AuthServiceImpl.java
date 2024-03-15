package com.dodeuni.dodeuni.service.auth;

import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserRepository;
import com.dodeuni.dodeuni.jwt.JwtTokenProvider;
import com.dodeuni.dodeuni.web.dto.auth.TokenResponseDto;
import com.dodeuni.dodeuni.web.dto.auth.UserSaveRequestDto;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public TokenResponseDto registerOrLogin(UserSaveRequestDto requestDto, HttpServletResponse response) {
        if (isExistingUser(requestDto.getEmail())) {
            User existingUser = userRepository.findByEmail(requestDto.getEmail());
            return issueToken(existingUser, response);
        }
        User newUser = userRepository.save(requestDto.toEntity());
        return issueToken(newUser, response);
    }

    private boolean isExistingUser(String email) {
        return userRepository.existsByEmail(email);
    }

    private TokenResponseDto issueToken(User user, HttpServletResponse response) {
        String token = jwtTokenProvider.generateToken(user);
        jwtTokenProvider.setHeaderAccessToken(response, token);
        return new TokenResponseDto(user, token);
    }
}
