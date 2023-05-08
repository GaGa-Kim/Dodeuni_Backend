package com.dodeuni.dodeuni.service;

import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserRepository;
import com.dodeuni.dodeuni.web.dto.user.TokenDto;
import com.dodeuni.dodeuni.web.dto.user.UserResponseDto;
import com.dodeuni.dodeuni.web.dto.user.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    
    public TokenDto login(UserSaveRequestDto userSaveRequestDto) {

        if(validateDuplicatedUser(userSaveRequestDto.getEmail())) {
            User user = userRepository.findByEmail(userSaveRequestDto.getEmail());
            return createToken(user);
        }
        else {
            User user = userRepository.save(userSaveRequestDto.toEntity());
            return createToken(user);
        }
    }

    public UserResponseDto getProfile(Long id) {

        User user = findUser(id);
        return new UserResponseDto(user);
    }

    public UserResponseDto updateNickname(Long id, String newNickname) {

        User user = findUser(id);
        user.updateProfile(newNickname);
        return new UserResponseDto(user);
    }

   public Boolean validateDuplicatedUser(String email) {

        return userRepository.countByEmail(email) > 0;
    }

    public User findUser(Long id) {

        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
    }

    public TokenDto createToken(User user) {
        
        // TODO : 추후 jwt 토큰 생성으로 변경
        String jwtToken = "jwt 토큰";

        return new TokenDto(user.getId(), user.getEmail(), user.getNickname(), jwtToken);
    }
}
