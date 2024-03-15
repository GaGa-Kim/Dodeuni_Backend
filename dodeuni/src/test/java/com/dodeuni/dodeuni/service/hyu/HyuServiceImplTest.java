package com.dodeuni.dodeuni.service.hyu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dodeuni.dodeuni.domain.hyu.Hyu;
import com.dodeuni.dodeuni.domain.hyu.HyuRepository;
import com.dodeuni.dodeuni.domain.hyu.HyuTest;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.service.user.UserService;
import com.dodeuni.dodeuni.web.dto.hyu.HyuResponseDto;
import com.dodeuni.dodeuni.web.dto.hyu.HyuSaveRequestDto;
import com.dodeuni.dodeuni.web.dto.hyu.HyuSaveRequestDtoTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HyuServiceImplTest {
    private Hyu hyu;

    @Mock
    private HyuRepository hyuRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private HyuServiceImpl hyuService;

    @BeforeEach
    void setUp() {
        hyu = HyuTest.testHyu();
        hyu.setUser(UserTest.testUser());
    }

    @Test
    @DisplayName("휴 게시글 저장 테스트")
    void testSaveHyu() {
        when(userService.findByUserId(anyLong())).thenReturn(hyu.getUser());
        when(hyuRepository.save(any(Hyu.class))).thenReturn(hyu);
        when(hyuRepository.findAllByOrderByCreatedDateTimeAsc()).thenReturn(List.of(hyu));

        HyuSaveRequestDto hyuSaveRequestDto = HyuSaveRequestDtoTest.testHyuSaveRequestDto(hyu);
        List<HyuResponseDto> result = hyuService.saveHyu(hyuSaveRequestDto);

        assertNotNull(result);
        assertEquals(hyu.getId(), result.get(0).getHyuId());
        assertEquals(hyu.getUser().getId(), result.get(0).getUserId());
        assertEquals(hyu.getContent(), result.get(0).getContent());
        assertEquals(hyu.getCreatedDateTime(), result.get(0).getCreatedDateTime());

        verify(userService).findByUserId(anyLong());
        verify(hyuRepository).save(any(Hyu.class));
        verify(hyuRepository).findAllByOrderByCreatedDateTimeAsc();
    }

    @Test
    @DisplayName("휴 전체 게시글 목록 조회 테스트")
    void testGetHyuList() {
        when(hyuRepository.findAllByOrderByCreatedDateTimeAsc()).thenReturn(List.of(hyu));

        List<HyuResponseDto> result = hyuService.getHyuList();

        assertNotNull(result);
        assertEquals(hyu.getId(), result.get(0).getHyuId());
        assertEquals(hyu.getUser().getId(), result.get(0).getUserId());
        assertEquals(hyu.getContent(), result.get(0).getContent());
        assertEquals(hyu.getCreatedDateTime(), result.get(0).getCreatedDateTime());

        verify(hyuRepository).findAllByOrderByCreatedDateTimeAsc();
    }

    @Test
    @DisplayName("휴 전체 게시글 테이블 삭제 테스트")
    void testDeleteHyuAll() {
        doNothing().when(hyuRepository).truncateHyuTable();

        hyuService.deleteHyuAll();

        verify(hyuRepository).truncateHyuTable();
    }
}