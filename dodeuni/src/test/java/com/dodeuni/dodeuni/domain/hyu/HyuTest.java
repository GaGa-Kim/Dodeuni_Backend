package com.dodeuni.dodeuni.domain.hyu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import com.dodeuni.dodeuni.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HyuTest {
    public static final Long HYU_ID = 1L;
    public static final String HYU_CONTENT = "휴 내용";

    public static Hyu testHyu() {
        return Hyu.builder()
                .id(HYU_ID)
                .content(HYU_CONTENT)
                .build();
    }

    private Hyu hyu;

    @BeforeEach
    void setUp() {
        hyu = testHyu();
    }

    @Test
    @DisplayName("휴 추가 테스트")
    void testHyuSave() {
        assertNotNull(hyu);
        assertEquals(HYU_ID, hyu.getId());
        assertEquals(HYU_CONTENT, hyu.getContent());
    }

    @Test
    @DisplayName("휴 게시글의 작성자 회원 연관관계 설정 테스트")
    void testSetUser() {
        User user = mock(User.class);
        hyu.setUser(user);

        assertEquals(user, hyu.getUser());
    }
}