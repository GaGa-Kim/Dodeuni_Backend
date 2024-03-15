package com.dodeuni.dodeuni.domain.community;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodeuni.dodeuni.except.UnexpectedValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MainTest {
    @Test
    @DisplayName("올바른 카테고리 대분류 열거형 값 테스트")
    void testFindByValidMainName() {
        String validMain = Main.INFORMATION.getMainName();

        Main main = Main.findMainName(validMain);

        assertNotNull(main);
        assertEquals(Main.INFORMATION, main);
    }

    @Test
    @DisplayName("올바르지 않은 카테고리 대분류 열거형 값 테스트")
    void testFindByInvalidMainName() {
        String invalidMain = "질문";

        assertThatThrownBy(() -> Main.findMainName(invalidMain))
                .isInstanceOf(UnexpectedValueException.class);
    }
}