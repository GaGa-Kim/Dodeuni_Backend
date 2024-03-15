package com.dodeuni.dodeuni.web;

import com.dodeuni.dodeuni.service.alarm.AlarmService;
import com.dodeuni.dodeuni.web.dto.alarm.AlarmResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Alarm API (알람 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/alarm")
public class AlarmController {
    private final AlarmService alarmService;

    @PostMapping
    @ApiOperation(notes = "회원의 기기-사용자 별 fcm 토큰 추가", value = "fcm 토큰 추가 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "회원 아이디", dataType = "Long", example = "1"),
            @ApiImplicitParam(name = "fcmToken", value = "기기-사용자 별 fcm 토큰", dataType = "String", example = "A1B2D3")
    })
    public ResponseEntity<Object> updateFcmToken(@RequestParam @NotNull Long userId,
                                                 @RequestParam @NotEmpty String fcmToken) {
        alarmService.updateFcmToken(userId, fcmToken);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @ApiOperation(notes = "회원이 작성한 커뮤니티에 달린 댓글에 대한 알람 목록 조회", value = "알람 목록 조회 API")
    @ApiImplicitParam(name = "userId", value = "회원 아이디", dataType = "Long", example = "1")
    public ResponseEntity<List<AlarmResponseDto>> commentAlarmList(@RequestParam @NotNull Long userId) {
        return ResponseEntity.ok().body(alarmService.getCommentAlarmListByCommunity(userId));
    }
}