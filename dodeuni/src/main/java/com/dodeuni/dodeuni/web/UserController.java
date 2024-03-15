package com.dodeuni.dodeuni.web;

import com.dodeuni.dodeuni.service.user.UserService;
import com.dodeuni.dodeuni.web.dto.user.UserResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"User API (회원 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    @ApiOperation(notes = "회원 프로필을 상세 조회", value = "회원 프로필 상세 조회 API")
    @ApiImplicitParam(name = "userId", value = "회원 아이디", dataType = "Long", example = "1")
    public ResponseEntity<UserResponseDto> userProfile(@RequestParam @NotNull Long userId) {
        return ResponseEntity.ok().body(userService.getUserProfile(userId));
    }

    @PutMapping
    @ApiOperation(notes = "회원 닉네임을 수정", value = "회원 닉네임 수정 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 아이디", dataType = "Long", example = "1"),
            @ApiImplicitParam(name = "newNickname", value = "새 닉네임", dataType = "String", example = "도드니니")
    })
    public ResponseEntity<UserResponseDto> updateNickname(@RequestParam @NotNull Long userId,
                                                          @RequestParam @NotEmpty String newNickname) {
        return ResponseEntity.ok().body(userService.updateNickname(userId, newNickname));
    }
}
