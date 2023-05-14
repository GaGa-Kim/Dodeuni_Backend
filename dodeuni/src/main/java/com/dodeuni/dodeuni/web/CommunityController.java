package com.dodeuni.dodeuni.web;

import com.dodeuni.dodeuni.service.CommunityService;
import com.dodeuni.dodeuni.web.dto.community.CommunityListResponseDto;
import com.dodeuni.dodeuni.web.dto.community.CommunityResponseDto;
import com.dodeuni.dodeuni.web.dto.community.CommunitySaveRequestDto;
import com.dodeuni.dodeuni.web.dto.community.CommunityUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CommunityController {

    private final CommunityService communityService;

    @PostMapping("/api/community")
    public ResponseEntity<CommunityResponseDto> save(@ModelAttribute CommunitySaveRequestDto communitySaveRequestDto) {
        return ResponseEntity.ok().body(communityService.save(communitySaveRequestDto));
    }

    @GetMapping("/api/community/list")
    public ResponseEntity<List<CommunityListResponseDto>> getList(@RequestParam String main, String sub) {
        return ResponseEntity.ok().body(communityService.getList(main, sub));
    }

    @GetMapping("/api/community/detail")
    public ResponseEntity<CommunityResponseDto> getDetail(@RequestParam Long id) {
        return ResponseEntity.ok().body(communityService.getDetail(id));
    }

    @PutMapping("/api/community")
    public ResponseEntity<CommunityResponseDto> update(@ModelAttribute CommunityUpdateRequestDto communityUpdateRequestDto) {
        if (!CollectionUtils.isEmpty(communityUpdateRequestDto.getDeletePhotoId())) {
            communityService.deletePhoto(communityUpdateRequestDto.getDeletePhotoId());
        }
        return ResponseEntity.ok().body(communityService.update(communityUpdateRequestDto));
    }

    @DeleteMapping("/api/community")
    public ResponseEntity<Object> delete(@RequestParam Long userId, Long id) {
        communityService.delete(userId, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
