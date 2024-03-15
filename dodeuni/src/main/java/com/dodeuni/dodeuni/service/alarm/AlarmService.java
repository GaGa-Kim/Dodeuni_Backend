package com.dodeuni.dodeuni.service.alarm;

import com.dodeuni.dodeuni.web.dto.alarm.AlarmResponseDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AlarmService {
    /**
     * 회원의 기기-사용자 별 fcm 토큰 추가한다.
     *
     * @param userId   (회원 아이디)
     * @param fcmToken (fcm 토큰)
     */
    void updateFcmToken(Long userId, String fcmToken);

    /**
     * 회원이 작성한 커뮤니티에 달린 댓글에 대한 알람 목록 조회한다.
     *
     * @param userId (회원 아이디)
     * @return List<AlarmResponseDto> (알람 정보를 담은 DTO 목록)
     */
    List<AlarmResponseDto> getCommentAlarmListByCommunity(Long userId);
}
