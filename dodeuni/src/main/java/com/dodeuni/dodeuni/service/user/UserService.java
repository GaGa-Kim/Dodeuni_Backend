package com.dodeuni.dodeuni.service.user;

import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.web.dto.user.UserResponseDto;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface UserService {
    /**
     * 회원 프로필을 상세 조회한다.
     *
     * @param userId (회원 아이디)
     * @return UserResponseDto (회원 정보를 담은 DTO)
     */
    UserResponseDto getUserProfile(Long userId);

    /**
     * 회원 닉네임을 수정한다.
     *
     * @param userId      (회원 아이디)
     * @param newNickname (회원 새 닉네임)
     * @return UserResponseDto (회원 정보를 담은 DTO)
     */
    UserResponseDto updateNickname(Long userId, String newNickname);

    /**
     * 회원을 조회한다.
     *
     * @param userId (회원 아이디)
     * @return User (회원)
     */
    User findByUserId(Long userId);
}