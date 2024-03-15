package com.dodeuni.dodeuni.web.dto.hyu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodeuni.dodeuni.domain.hyu.Hyu;
import com.dodeuni.dodeuni.domain.hyu.HyuTest;
import com.dodeuni.dodeuni.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HyuResponseDtoTest {
    private Hyu hyu;

    public static HyuResponseDto testHyuResponseDto(Hyu hyu) {
        return new HyuResponseDto(hyu);
    }

    @BeforeEach
    void setUp() {
        hyu = HyuTest.testHyu();
        hyu.setUser(UserTest.testUser());
    }

    @Test
    @DisplayName("HyuResponseDto 생성 테스트")
    void testHyuResponseDtoSave() {
        HyuResponseDto hyuResponseDto = testHyuResponseDto(hyu);

        assertNotNull(hyuResponseDto);
        assertEquals(hyu.getId(), hyuResponseDto.getHyuId());
        assertEquals(hyu.getUser().getId(), hyuResponseDto.getUserId());
        assertEquals(hyu.getContent(), hyuResponseDto.getContent());
        assertEquals(hyu.getCreatedDateTime(), hyuResponseDto.getCreatedDateTime());
    }
}