package com.dodeuni.dodeuni.domain.hyu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodeuni.dodeuni.domain.user.UserRepository;
import com.dodeuni.dodeuni.domain.user.UserTest;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HyuRepositoryTest {
    private Hyu hyu;

    @Autowired
    private HyuRepository hyuRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        hyu = HyuTest.testHyu();
        hyu.setUser(userRepository.save(UserTest.testUser()));
        hyu = hyuRepository.save(hyu);
    }

    @AfterEach
    void clean() {
        hyuRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    @DisplayName("생성 시간으로 정렬한 휴 전체 목록 조회 테스트")
    void testFindAllByOrderByCreatedDateTimeAsc() {
        List<Hyu> foundHyuList = hyuRepository.findAllByOrderByCreatedDateTimeAsc();

        assertNotNull(foundHyuList);
        assertEquals(1, foundHyuList.size());
    }

    @Test
    @DisplayName("휴 전체 테이블 삭제 테스트")
    void testTruncateHyuTable() {
        hyuRepository.truncateHyuTable();

        List<Hyu> foundHyuList = hyuRepository.findAllByOrderByCreatedDateTimeAsc();

        assertNotNull(foundHyuList);
        assertEquals(0, foundHyuList.size());
    }
}