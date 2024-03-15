package com.dodeuni.dodeuni.service.comment;

import com.dodeuni.dodeuni.domain.comment.Comment;
import com.dodeuni.dodeuni.domain.comment.CommentRepository;
import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.except.NotFoundException;
import com.dodeuni.dodeuni.service.alarm.FcmSender;
import com.dodeuni.dodeuni.service.community.CommunityService;
import com.dodeuni.dodeuni.service.user.UserService;
import com.dodeuni.dodeuni.web.dto.comment.CommentResponseDto;
import com.dodeuni.dodeuni.web.dto.comment.CommentSaveRequestDto;
import com.dodeuni.dodeuni.web.dto.comment.CommentUpdateRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {
    private static final String COMMENT_ALARM_MESSAGE = "님이 회원님의 게시글에 댓글을 달았습니다.";

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final CommunityService communityService;
    private final FcmSender fcmSender;

    @Override
    public List<CommentResponseDto> saveComment(CommentSaveRequestDto requestDto) {
        Community community = communityService.findByCommunityId(requestDto.getCommunityId());
        User user = userService.findByUserId(requestDto.getUserId());
        createComment(community, user, requestDto);
        sendFcmAlarm(community, user);
        return getCommentListByCommunity(community);
    }

    @Override
    public List<CommentResponseDto> getCommentList(Long communityId) {
        Community community = communityService.findByCommunityId(communityId);
        return getCommentListByCommunity(community);
    }

    @Override
    public List<Comment> getAllCommentList(List<Community> communityList) {
        return commentRepository.findAllByCommunityInOrderByCreatedDateTimeDesc(communityList);
    }

    @Override
    public List<CommentResponseDto> updateComment(CommentUpdateRequestDto requestDto) {
        Comment comment = findByCommentId(requestDto.getCommentId());
        comment.updateComment(requestDto.getContent());
        Community community = communityService.findByCommunityId(requestDto.getCommunityId());
        return getCommentListByCommunity(community);
    }

    @Override
    public List<CommentResponseDto> deleteComment(Long commentId, Long communityId) {
        commentRepository.deleteById(commentId);
        Community community = communityService.findByCommunityId(communityId);
        return getCommentListByCommunity(community);
    }

    private void createComment(Community community, User user, CommentSaveRequestDto requestDto) {
        Comment comment = requestDto.toEntity();
        comment.setCommunity(community);
        comment.setUser(user);
        commentRepository.save(comment);
    }

    private void sendFcmAlarm(Community community, User user) {
        String alarmMessage = user.getNickname() + COMMENT_ALARM_MESSAGE;
        fcmSender.sendMessage(community.getUser().getFcmToken(), alarmMessage, community.getId());
        log.info(community.getUser().getNickname() + "님 " + alarmMessage);
    }

    private Comment findByCommentId(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(NotFoundException::new);
    }

    private List<CommentResponseDto> getCommentListByCommunity(Community community) {
        return commentRepository.findAllByCommunityOrderByCreatedDateTimeAsc(community)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
}
