package com.dodeuni.dodeuni.web;

import com.dodeuni.dodeuni.service.auth.AuthService;
import com.dodeuni.dodeuni.web.dto.auth.TokenResponseDto;
import com.dodeuni.dodeuni.web.dto.auth.UserSaveRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Auth API (회원 가입 ・ 로그인 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    @ApiOperation(notes = "소셜 회원 가입 또는 로그인을 진행한 후 JWT 토큰과 회원 정보를 발급", value = "소셜 회원가입 또는 로그인 API")
    public ResponseEntity<TokenResponseDto> registerOrLogin(@RequestBody @Valid UserSaveRequestDto requestDto,
                                                            HttpServletResponse response) {
        return ResponseEntity.ok().body(authService.registerOrLogin(requestDto, response));
    }
}