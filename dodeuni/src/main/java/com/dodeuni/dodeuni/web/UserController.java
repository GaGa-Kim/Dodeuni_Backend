package com.dodeuni.dodeuni.web;

import com.dodeuni.dodeuni.service.UserService;
import com.dodeuni.dodeuni.web.dto.user.TokenDto;
import com.dodeuni.dodeuni.web.dto.user.UserResponseDto;
import com.dodeuni.dodeuni.web.dto.user.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    @PostMapping("/auth/login")
    public ResponseEntity<TokenDto> login(@RequestBody UserSaveRequestDto userSaveRequestDto) {
        return ResponseEntity.ok().body(userService.login(userSaveRequestDto));
    }

    @GetMapping("/api/user")
    public ResponseEntity<UserResponseDto> getProfile(@RequestParam Long id) {
        return ResponseEntity.ok().body(userService.getProfile(id));
    }

    @PutMapping("/api/user")
    public ResponseEntity<UserResponseDto> updateNickname(@RequestParam Long id, String newNickname) {
        return ResponseEntity.ok().body(userService.updateNickname(id, newNickname));
    }
}
