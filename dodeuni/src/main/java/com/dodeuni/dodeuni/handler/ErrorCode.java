package com.dodeuni.dodeuni.handler;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    SUCCESS(HttpStatus.OK, "정상적으로 처리되었습니다."),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 소셜 로그인 액세스 토큰입니다."),

    JWT_FORBIDDEN(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    JWT_UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "인증되지 않은 사용자입니다."),
    JWT_ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "액세스 토큰이 만료되었습니다."),

    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 정보를 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}