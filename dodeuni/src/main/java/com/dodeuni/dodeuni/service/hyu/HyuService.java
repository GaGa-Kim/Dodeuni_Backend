package com.dodeuni.dodeuni.service.hyu;

import com.dodeuni.dodeuni.web.dto.hyu.HyuResponseDto;
import com.dodeuni.dodeuni.web.dto.hyu.HyuSaveRequestDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface HyuService {
    /**
     * 휴 게시글을 저장한다.
     *
     * @param requestDto (휴 게시글 저장 정보를 담은 DTO)
     * @return List<HyuResponseDto> (휴 게시글 정보를 담은 DTO)
     */
    List<HyuResponseDto> saveHyu(HyuSaveRequestDto requestDto);

    /**
     * 휴 전체 게시글 목록을 조회한다.
     *
     * @return List<HyuResponseDto> (휴 게시글 정보를 담은 DTO)
     */
    @Transactional(readOnly = true)
    List<HyuResponseDto> getHyuList();

    /**
     * 매일 자정 휴 게시글 테이블을 전체 삭제한다.
     */
    void deleteHyuAll();
}
