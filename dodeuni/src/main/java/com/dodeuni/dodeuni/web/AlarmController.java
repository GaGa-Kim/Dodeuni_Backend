package com.dodeuni.dodeuni.web;

import com.dodeuni.dodeuni.service.AlarmService;
import com.dodeuni.dodeuni.web.dto.alarm.AlarmResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AlarmController {

    private final AlarmService alarmService;

    @GetMapping("/api/alarm")
    public ResponseEntity<List<AlarmResponseDto>> findCommentByCommunity(@RequestParam Long userId) {
        return ResponseEntity.ok().body(alarmService.findCommentByCommunity(userId));
    }

    @PostMapping("/api/alarm")
    public ResponseEntity<Object> getFcmToken(@RequestParam Long userId, String fcmToken) {
        alarmService.getFcmToken(userId, fcmToken);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
