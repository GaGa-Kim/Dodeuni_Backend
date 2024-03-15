package com.dodeuni.dodeuni.domain.place;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.dodeuni.dodeuni.except.UnexpectedValueException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryTest {
    @Test
    @DisplayName("올바른 장소 추천 카테고리 열거형 값 테스트")
    void testFindByValidCategoryName() {
        String validCategory = Category.CENTER.getCategoryName();

        Category category = Category.findCategoryName(validCategory);

        assertNotNull(category);
        assertEquals(Category.CENTER, category);
    }

    @Test
    @DisplayName("올바르지 않은 장소 추천 카테고리 열거형 값 테스트")
    void testFindByInvalidCategoryName() {
        String invalidCategory = "숙소";

        assertThatThrownBy(() -> Category.findCategoryName(invalidCategory))
                .isInstanceOf(UnexpectedValueException.class);
    }
}