package com.dodeuni.dodeuni.domain.community;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Sub {
    EXCHANGE("정보교환"),
    WORRY("고민상담"),
    REVIEW("제품후기"),
    INFORMATION("정보"),
    MARKET("장터"),
    GATHERING("모임");

    private final String subName;

    private static final Map<String, Sub> SUB_MAIN_MAP = new HashMap<>();

    static {
        for (Sub sub : Sub.values()) {
            SUB_MAIN_MAP.put(sub.getSubName(), sub);
        }
    }

    public static Sub findSubName(String subName) {
        return SUB_MAIN_MAP.get(subName);
    }
}