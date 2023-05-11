package com.dodeuni.dodeuni.service;

import com.dodeuni.dodeuni.domain.comment.Comment;
import com.dodeuni.dodeuni.domain.comment.CommentRepository;
import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.domain.community.CommunityRepository;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserRepository;
import com.dodeuni.dodeuni.web.dto.alarm.AlarmResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AlarmService {

    private final UserRepository userRepository;
    private final CommunityRepository communityRepository;
    private final CommentRepository commentRepository;

    public List<AlarmResponseDto> findCommentByCommunity(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        List<Comment> commentList = communityRepository.findByUserId(user).stream()
                .flatMap(community -> commentRepository.findByCommunity(community).stream())
                .collect(Collectors.toList());

        Collections.sort(commentList, new TimeComparator());
        return commentList.stream().map(AlarmResponseDto::new).collect(Collectors.toList());
    }

    static class TimeComparator implements Comparator<Comment> {

        @Override
        public int compare(Comment comment1, Comment comment2) {

            LocalDateTime localDateTime1 = comment1.getCreatedDateTime();
            LocalDateTime localDateTime2 = comment2.getCreatedDateTime();

            int result = localDateTime2.compareTo(localDateTime1);
            return result;
        }
    }
}
