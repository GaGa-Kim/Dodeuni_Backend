package com.dodeuni.dodeuni.domain.community;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodeuni.dodeuni.except.UnexpectedValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SubTest {
    @Test
    @DisplayName("올바른 카테고리 소분류 열거형 값 테스트")
    void testFindByValidSubName() {
        String validSub = Sub.EXCHANGE.getSubName();

        Sub sub = Sub.findSubName(validSub);

        assertNotNull(sub);
        assertEquals(Sub.EXCHANGE, sub);
    }

    @Test
    @DisplayName("올바르지 않은 카테고리 소분류 열거형 값 테스트")
    void testFindByInvalidSubName() {
        String invalidSub = "질문";

        assertThatThrownBy(() -> Sub.findSubName(invalidSub))
                .isInstanceOf(UnexpectedValueException.class);
    }
}