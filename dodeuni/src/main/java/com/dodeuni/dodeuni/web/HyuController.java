package com.dodeuni.dodeuni.web;

import com.dodeuni.dodeuni.service.hyu.HyuService;
import com.dodeuni.dodeuni.web.dto.hyu.HyuResponseDto;
import com.dodeuni.dodeuni.web.dto.hyu.HyuSaveRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"Hyu API (휴 API)"})
@RestController
@Validated
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/hyus")
public class HyuController {
    private final HyuService hyuService;

    @PostMapping
    @ApiOperation(notes = "휴 게시글을 저장", value = "휴 게시글 저장 API")
    @ApiImplicitParam(name = "requestDto", value = "댓글 저장 정보를 담은 DTO")
    public ResponseEntity<List<HyuResponseDto>> save(@RequestBody @Valid HyuSaveRequestDto requestDto) {
        return ResponseEntity.ok().body(hyuService.saveHyu(requestDto));
    }

    @GetMapping("/list")
    @ApiOperation(notes = "휴 전체 게시글 목록을 조회", value = "휴 목록 조회 API")
    public ResponseEntity<List<HyuResponseDto>> list() {
        return ResponseEntity.ok().body(hyuService.getHyuList());
    }
}