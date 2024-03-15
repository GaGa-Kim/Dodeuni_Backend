package com.dodeuni.dodeuni.service.hyu;

import com.dodeuni.dodeuni.domain.hyu.Hyu;
import com.dodeuni.dodeuni.domain.hyu.HyuRepository;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.service.user.UserServiceImpl;
import com.dodeuni.dodeuni.web.dto.hyu.HyuResponseDto;
import com.dodeuni.dodeuni.web.dto.hyu.HyuSaveRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HyuServiceImpl implements HyuService {
    private final HyuRepository hyuRepository;
    private final UserServiceImpl userServiceImpl;

    @Override
    public List<HyuResponseDto> saveHyu(HyuSaveRequestDto requestDto) {
        User user = userServiceImpl.findByUserId(requestDto.getUserId());
        Hyu hyu = requestDto.toEntity();
        hyu.setUser(user);
        hyuRepository.save(hyu);
        return getHyuList();
    }

    @Override
    public List<HyuResponseDto> getHyuList() {
        return hyuRepository.findAllByOrderByCreatedDateTimeAsc()
                .stream()
                .map(HyuResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteHyuAll() {
        hyuRepository.truncateHyuTable();
    }
}
