package com.dodeuni.dodeuni.service.user;

import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserRepository;
import com.dodeuni.dodeuni.except.NotFoundException;
import com.dodeuni.dodeuni.web.dto.user.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserResponseDto getUserProfile(Long userId) {
        User user = findByUserId(userId);
        return new UserResponseDto(user);
    }

    public UserResponseDto updateNickname(Long userId, String newNickname) {
        User user = findByUserId(userId);
        user.updateProfile(newNickname);
        return new UserResponseDto(user);
    }

    public User findByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(NotFoundException::new);
    }
}
