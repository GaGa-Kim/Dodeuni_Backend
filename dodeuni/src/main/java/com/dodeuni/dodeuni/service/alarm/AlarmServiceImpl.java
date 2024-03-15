package com.dodeuni.dodeuni.service.alarm;

import com.dodeuni.dodeuni.domain.comment.Comment;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.service.comment.CommentService;
import com.dodeuni.dodeuni.service.user.UserService;
import com.dodeuni.dodeuni.web.dto.alarm.AlarmResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlarmServiceImpl implements AlarmService {
    private final UserService userService;
    private final CommentService commentService;

    @Override
    public void updateFcmToken(Long userId, String fcmToken) {
        User user = userService.findByUserId(userId);
        user.updateFcmToken(fcmToken);
    }

    @Override
    public List<AlarmResponseDto> getCommentAlarmListByCommunity(Long userId) {
        User user = userService.findByUserId(userId);
        List<Comment> commentList = commentService.getAllCommentList(user.getCommunityList());
        return commentList
                .stream()
                .map(AlarmResponseDto::new)
                .collect(Collectors.toList());
    }
}