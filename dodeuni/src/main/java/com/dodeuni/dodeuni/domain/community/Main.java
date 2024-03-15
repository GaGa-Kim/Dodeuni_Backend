package com.dodeuni.dodeuni.domain.community;

import com.dodeuni.dodeuni.except.UnexpectedValueException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Main {
    INFORMATION("정보"),
    MARKET("장터"),
    GATHERING("모임");

    private final String mainName;
    
    private static final Map<String, Main> MAIN_MAP = new HashMap<>();

    static {
        for (Main main : Main.values()) {
            MAIN_MAP.put(main.getMainName(), main);
        }
    }

    public static Main findMainName(String mainName) {
        Main foundMain = MAIN_MAP.get(mainName);
        if (foundMain == null) {
            throw new UnexpectedValueException();
        }
        return foundMain;
    }
}
