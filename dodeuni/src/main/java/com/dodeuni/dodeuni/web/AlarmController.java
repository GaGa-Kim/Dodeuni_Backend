package com.dodeuni.dodeuni.web;

import com.dodeuni.dodeuni.service.AlarmService;
import com.dodeuni.dodeuni.web.dto.alarm.AlarmResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
}