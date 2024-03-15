package com.dodeuni.dodeuni.domain.place;

import com.dodeuni.dodeuni.except.UnexpectedValueException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {
    USER("추천"), // 사용자 추천
    CENTER("센터"),
    KINDERGARTEN("유치원"),
    SCHOOL("학교"),
    HOSPITAL("어린이 병원");

    private final String categoryName;

    private static final Map<String, Category> CATEGORY_MAP = new HashMap<>();

    static {
        for (Category category : Category.values()) {
            CATEGORY_MAP.put(category.getCategoryName(), category);
        }
    }

    public static Category findCategoryName(String categoryName) {
        Category foundCategory = CATEGORY_MAP.get(categoryName);
        if (foundCategory == null) {
            throw new UnexpectedValueException();
        }
        return foundCategory;
    }
}
