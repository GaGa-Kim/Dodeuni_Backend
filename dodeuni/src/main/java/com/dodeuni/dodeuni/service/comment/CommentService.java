package com.dodeuni.dodeuni.service.comment;

import com.dodeuni.dodeuni.domain.comment.Comment;
import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.web.dto.comment.CommentResponseDto;
import com.dodeuni.dodeuni.web.dto.comment.CommentSaveRequestDto;
import com.dodeuni.dodeuni.web.dto.comment.CommentUpdateRequestDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CommentService {
    /**
     * 커뮤니티 게시글의 댓글을 저장한다.
     *
     * @param requestDto (댓글 저장 정보를 담은 DTO)
     * @return List<CommentResponseDto> (댓글 정보를 담은 DTO)
     */
    List<CommentResponseDto> saveComment(CommentSaveRequestDto requestDto);

    /**
     * 커뮤니티 게시글 아이디에 따른 댓글 목록을 조회한다.
     *
     * @param communityId (커뮤니티 게시글 아이디)
     * @return List<CommentResponseDto> (댓글 정보를 담은 DTO)
     */
    @Transactional(readOnly = true)
    List<CommentResponseDto> getCommentList(Long communityId);

    /**
     * 회원이 작성한 커뮤니티 게시글 목록에 달린 댓글 목록에 대해 내림차순으로 정렬해 조회한다.
     *
     * @param communityList (회원이 작성한 커뮤니티 게시글 아이디)
     * @return List<Comment> (생성된)
     */
    @Transactional(readOnly = true)
    List<Comment> getAllCommentList(List<Community> communityList);

    /**
     * 커뮤니티 게시글의 댓글을 수정한다.
     *
     * @param requestDto (댓글 수정 정보를 담은 DTO)
     * @return List<CommentResponseDto> (댓글 정보를 담은 DTO)
     */
    List<CommentResponseDto> updateComment(CommentUpdateRequestDto requestDto);

    /**
     * 커뮤니티 게시글의 댓글을 삭제한다.
     *
     * @param commentId   (댓글 아이디)
     * @param communityId (커뮤니티 게시글 아이디)
     * @return List<CommentResponseDto> (댓글 정보를 담은 DTO)
     */
    List<CommentResponseDto> deleteComment(Long commentId, Long communityId);
}
