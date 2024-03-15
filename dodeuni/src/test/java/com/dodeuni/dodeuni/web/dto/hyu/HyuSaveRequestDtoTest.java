package com.dodeuni.dodeuni.web.dto.hyu;

import static com.dodeuni.dodeuni.web.dto.auth.UserSaveRequestDtoTest.INVALID_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.dodeuni.dodeuni.domain.hyu.Hyu;
import com.dodeuni.dodeuni.domain.hyu.HyuTest;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.web.dto.ValidatorUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HyuSaveRequestDtoTest {
    private final ValidatorUtil<HyuSaveRequestDto> validatorUtil = new ValidatorUtil<>();

    private Hyu hyu;

    public static HyuSaveRequestDto testHyuSaveRequestDto(Hyu hyu) {
        return HyuSaveRequestDto.builder()
                .userId(hyu.getUser().getId())
                .content(hyu.getContent())
                .build();
    }

    @BeforeEach
    void setUp() {
        hyu = HyuTest.testHyu();
        hyu.setUser(UserTest.testUser());
    }

    @Test
    @DisplayName("HyuSaveRequestDto 생성 테스트")
    void testHyuSaveRequestDtoSave() {
        HyuSaveRequestDto hyuSaveRequestDto = testHyuSaveRequestDto(hyu);

        assertNotNull(hyuSaveRequestDto);
        assertEquals(hyu.getUser().getId(), hyuSaveRequestDto.getUserId());
        assertEquals(hyu.getContent(), hyuSaveRequestDto.getContent());
    }

    @Test
    @DisplayName("HyuSaveRequestDto toEntity 테스트")
    void testHyuSaveRequestDtoToEntity() {
        HyuSaveRequestDto hyuSaveRequestDto = testHyuSaveRequestDto(hyu);

        Hyu hyuEntity = hyuSaveRequestDto.toEntity();
        hyuEntity.setUser(hyu.getUser());

        assertNotNull(hyuSaveRequestDto);
        assertEquals(hyuSaveRequestDto.getUserId(), hyuEntity.getUser().getId());
        assertEquals(hyuSaveRequestDto.getContent(), hyuEntity.getContent());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        HyuSaveRequestDto hyuSaveRequestDto = new HyuSaveRequestDto();

        assertNotNull(hyuSaveRequestDto);
        assertNull(hyuSaveRequestDto.getUserId());
        assertNull(hyuSaveRequestDto.getContent());
    }

    @Test
    @DisplayName("회원 아이디 유효성 검증 테스트")
    void userId_validation() {
        HyuSaveRequestDto hyuSaveRequestDto = HyuSaveRequestDto.builder()
                .userId(null)
                .content(hyu.getContent())
                .build();

        validatorUtil.validate(hyuSaveRequestDto);
    }

    @Test
    @DisplayName("휴 내용 유효성 검증 테스트")
    void content_validation() {
        HyuSaveRequestDto hyuSaveRequestDto = HyuSaveRequestDto.builder()
                .userId(hyu.getUser().getId())
                .content(INVALID_EMPTY)
                .build();

        validatorUtil.validate(hyuSaveRequestDto);
    }
}