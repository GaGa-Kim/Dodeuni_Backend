package com.dodeuni.dodeuni.domain.user;

import com.dodeuni.dodeuni.except.UnexpectedValueException;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    USER("일반 사용자"),
    MANAGER("관리자");

    private final String roleName;

    private static final Map<String, Role> ROLE_MAP = new HashMap<>();

    static {
        for (Role role : Role.values()) {
            ROLE_MAP.put(role.getRoleName(), role);
        }
    }

    public static Role findByRoleName(String role) {
        Role foundRole = ROLE_MAP.get(role);
        if (foundRole == null) {
            throw new UnexpectedValueException();
        }
        return foundRole;
    }
}