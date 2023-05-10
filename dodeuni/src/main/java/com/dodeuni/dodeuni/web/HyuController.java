package com.dodeuni.dodeuni.web;

import com.dodeuni.dodeuni.service.HyuService;
import com.dodeuni.dodeuni.web.dto.hyu.HyuResponseDto;
import com.dodeuni.dodeuni.web.dto.hyu.HyuSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class HyuController {

    private final HyuService hyuService;

    /* 휴 글 등록 */
    @PostMapping("/api/hyus")
    public List<HyuResponseDto> saveHyu(@RequestBody HyuSaveRequestDto requestDto){
        return hyuService.saveHyu(requestDto);
    }

    /* 휴 글 리스트 조회 */
    @GetMapping("/api/hyus/list")
    public List<HyuResponseDto> getHyuList(){
        return hyuService.getHyuList();
    }

}
