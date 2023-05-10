package com.dodeuni.dodeuni.web;

import com.dodeuni.dodeuni.service.CommentService;
import com.dodeuni.dodeuni.web.dto.comment.CommentResponseDto;
import com.dodeuni.dodeuni.web.dto.comment.CommentSaveRequestDto;
import com.dodeuni.dodeuni.web.dto.comment.CommentUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    /* 댓글 등록 */
    @PostMapping("/api/comments")
    public List<CommentResponseDto> saveComment(@RequestBody CommentSaveRequestDto requestDto){
        return commentService.saveComment(requestDto);
    }

    /* 댓글 리스트 조회 */
    @GetMapping("/api/comments/list/{cid}")
    public List<CommentResponseDto> getCommentList(@PathVariable("cid") Long cid) throws Exception{
        return this.commentService.getCommentList(cid);
    }

    /* 댓글 수정 */
    @PutMapping("/api/comments")
    public List<CommentResponseDto> updateComment(@RequestBody CommentUpdateRequestDto requestDto){
        return commentService.updateComment(requestDto);
    }

    /* 댓글 삭제 */
    @DeleteMapping("/api/comments/{id}/{cid}")
    public List<CommentResponseDto> deleteComment(
            @PathVariable("id") Long id,
            @PathVariable("cid") Long cid
    ){
        return commentService.deleteComment(id, cid);
    }



}
