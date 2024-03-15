package com.dodeuni.dodeuni.domain.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {
    private User user;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user = UserTest.testUser();
        user = userRepository.save(user);
    }

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("이메일로 회원 존재 유무 조회 테스트")
    void testExistsByEmail() {
        boolean foundUser = userRepository.existsByEmail(user.getEmail());

        assertTrue(foundUser);
    }

    @Test
    @DisplayName("이메일로 회원 조회 테스트")
    void testFindByEmail() {
        User foundUser = userRepository.findByEmail(user.getEmail());

        assertNotNull(foundUser);
        assertEquals(user.getEmail(), foundUser.getEmail());
        assertEquals(user.getNickname(), foundUser.getNickname());
        assertNull(foundUser.getFcmToken());
        assertEquals(user.getRole(), foundUser.getRole());
    }
}