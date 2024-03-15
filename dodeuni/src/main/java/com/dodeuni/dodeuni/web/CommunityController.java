package com.dodeuni.dodeuni.web;

import com.dodeuni.dodeuni.service.community.CommunityService;
import com.dodeuni.dodeuni.web.dto.community.CommunityListResponseDto;
import com.dodeuni.dodeuni.web.dto.community.CommunityResponseDto;
import com.dodeuni.dodeuni.web.dto.community.CommunitySaveRequestDto;
import com.dodeuni.dodeuni.web.dto.community.CommunityUpdateRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Community API (커뮤니티 게시글 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/communities")
public class CommunityController {
    private final CommunityService communityService;

    @PostMapping
    @ApiOperation(notes = "커뮤니티 게시글을 저장", value = "커뮤니티 게시글 저장 API")
    @ApiImplicitParam(name = "requestDto", value = "커뮤니티 게시글 저장 정보를 담은 DTO")
    public ResponseEntity<CommunityResponseDto> save(@RequestBody @Valid CommunitySaveRequestDto requestDto) {
        return ResponseEntity.ok().body(communityService.saveCommunity(requestDto));
    }

    @GetMapping("/list")
    @ApiOperation(notes = "커뮤니티 카테고리 대분류와 소분류에 따른 게시글 목록을 조회", value = "커뮤니티 게시글 목록 조회 API")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "main", value = "커뮤니티 카테고리 대분류", dataType = "String", example = "정보"),
            @ApiImplicitParam(name = "sub", value = "커뮤니티 카테고리 소분류", dataType = "String", example = "정보교환")
    })
    public ResponseEntity<List<CommunityListResponseDto>> list(@RequestParam @NotEmpty String main,
                                                               @RequestParam @NotEmpty String sub) {
        return ResponseEntity.ok().body(communityService.getCommunityList(main, sub));
    }

    @GetMapping("/{communityId}")
    @ApiOperation(notes = "커뮤니티 게시글을 상세 조회", value = "커뮤니티 게시글 조회 API")
    @ApiImplicitParam(name = "communityId", value = "커뮤니티 게시글 아이디", dataType = "Long", example = "1")
    public ResponseEntity<CommunityResponseDto> view(@PathVariable @NotNull Long communityId) {
        return ResponseEntity.ok().body(communityService.getCommunity(communityId));
    }

    @PutMapping
    @ApiOperation(notes = "커뮤니티 게시글을 수정", value = "커뮤니티 게시글 수정 API")
    @ApiImplicitParam(name = "requestDto", value = "댓글 수정 정보를 담은 DTO")
    public ResponseEntity<CommunityResponseDto> update(@RequestBody @Valid CommunityUpdateRequestDto requestDto) {
        return ResponseEntity.ok().body(communityService.updateCommunity(requestDto));
    }

    @DeleteMapping
    @ApiOperation(notes = "커뮤니티 게시글을 삭제", value = "커뮤니티 게시글 삭제 API")
    @ApiImplicitParam(name = "commentId", value = "댓글 아이디", dataType = "Long", example = "1")
    public ResponseEntity<Object> delete(@RequestParam @NotNull Long communityId) {
        communityService.deleteCommunity(communityId);
        return ResponseEntity.noContent().build();
    }
}
