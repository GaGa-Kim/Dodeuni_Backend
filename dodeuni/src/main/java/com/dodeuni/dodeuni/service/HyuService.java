package com.dodeuni.dodeuni.service;

import com.dodeuni.dodeuni.domain.hyu.Hyu;
import com.dodeuni.dodeuni.domain.hyu.HyuRepository;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserRepository;
import com.dodeuni.dodeuni.web.dto.hyu.HyuResponseDto;
import com.dodeuni.dodeuni.web.dto.hyu.HyuSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class HyuService {
    private final UserRepository userRepository;
    private final HyuRepository hyuRepository;

    /* 휴 글 등록 */
    @Transactional
    public List<HyuResponseDto> saveHyu(HyuSaveRequestDto requestDto){
        User user = userRepository.findById(requestDto.getUid())
                .orElseThrow(IllegalArgumentException::new);

        Hyu hyus = requestDto.toEntity();
        hyus.setUser(user);
        user.addHyuList(hyuRepository.save(hyus));

        return hyuRepository.findAllByOrderByCreatedDateTimeAsc()
                .stream()
                .map(HyuResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 휴 글 리스트 조회 */
    @Transactional(readOnly = true)
    public List<HyuResponseDto> getHyuList(){
        return hyuRepository.findAllByOrderByCreatedDateTimeAsc()
                .stream()
                .map(HyuResponseDto::new)
                .collect(Collectors.toList());
    }

    /* 휴 글 전체 삭제 */
    @Scheduled(cron = "0 0 24 * * ?")
    //@Scheduled(cron = "0 44 23 * * ?")
    public void deleteHyuAll(){
        hyuRepository.truncateHyuTable();
    }
}
