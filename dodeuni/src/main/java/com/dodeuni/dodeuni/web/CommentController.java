package com.dodeuni.dodeuni.web;

import com.dodeuni.dodeuni.service.comment.CommentService;
import com.dodeuni.dodeuni.web.dto.comment.CommentResponseDto;
import com.dodeuni.dodeuni.web.dto.comment.CommentSaveRequestDto;
import com.dodeuni.dodeuni.web.dto.comment.CommentUpdateRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Comment API (커뮤니티 게시글 댓글 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @ApiOperation(notes = "커뮤니티 게시글의 댓글을 저장", value = "댓글 저장 API")
    @ApiImplicitParam(name = "requestDto", value = "댓글 저장 정보를 담은 DTO")
    public ResponseEntity<List<CommentResponseDto>> save(@RequestBody @Valid CommentSaveRequestDto requestDto) {
        return ResponseEntity.ok().body(commentService.saveComment(requestDto));
    }

    @GetMapping("/list")
    @ApiOperation(notes = "커뮤니티 게시글 아이디에 따른 댓글 목록을 조회", value = "댓글 목록 조회 API")
    @ApiImplicitParam(name = "communityId", value = "커뮤니티 게시글 아이디", dataType = "Long", example = "1")
    public ResponseEntity<List<CommentResponseDto>> list(@RequestParam @NotNull Long communityId) throws Exception {
        return ResponseEntity.ok().body(commentService.getCommentList(communityId));
    }

    @PutMapping
    @ApiOperation(notes = "커뮤니티 게시글의 댓글을 수정", value = "댓글 수정 API")
    @ApiImplicitParam(name = "requestDto", value = "댓글 수정 정보를 담은 DTO")
    public ResponseEntity<List<CommentResponseDto>> update(@RequestBody @Valid CommentUpdateRequestDto requestDto) {
        return ResponseEntity.ok().body(commentService.updateComment(requestDto));
    }

    @DeleteMapping
    @ApiOperation(notes = "커뮤니티 게시글의 댓글을 삭제", value = "댓글 삭제 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "댓글 아이디", dataType = "Long", example = "1"),
            @ApiImplicitParam(name = "communityId", value = "커뮤니티 게시글 아이디", dataType = "Long", example = "1")
    })
    public ResponseEntity<List<CommentResponseDto>> delete(@RequestParam @NotNull Long commentId,
                                                           @RequestParam @NotNull Long communityId) {
        return ResponseEntity.ok().body(commentService.deleteComment(commentId, communityId));
    }
}
