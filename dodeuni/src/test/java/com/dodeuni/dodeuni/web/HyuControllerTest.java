package com.dodeuni.dodeuni.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.dodeuni.dodeuni.config.SecurityConfig;
import com.dodeuni.dodeuni.domain.hyu.Hyu;
import com.dodeuni.dodeuni.domain.hyu.HyuTest;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.jwt.JwtRequestFilter;
import com.dodeuni.dodeuni.service.hyu.HyuService;
import com.dodeuni.dodeuni.web.dto.hyu.HyuResponseDto;
import com.dodeuni.dodeuni.web.dto.hyu.HyuResponseDtoTest;
import com.dodeuni.dodeuni.web.dto.hyu.HyuSaveRequestDto;
import com.dodeuni.dodeuni.web.dto.hyu.HyuSaveRequestDtoTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = HyuController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtRequestFilter.class)})
@WithMockUser
class HyuControllerTest {
    private Hyu hyu;

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private HyuService hyuService;

    @BeforeEach
    void setUp() {
        hyu = HyuTest.testHyu();
        hyu.setUser(UserTest.testUser());
    }


    @Test
    @DisplayName("휴 게시글 저장 테스트")
    void testSave() throws Exception {
        List<HyuResponseDto> hyuResponseDtoList = List.of(HyuResponseDtoTest.testHyuResponseDto(hyu));
        when(hyuService.saveHyu(any())).thenReturn(hyuResponseDtoList);

        HyuSaveRequestDto hyuSaveRequestDto = HyuSaveRequestDtoTest.testHyuSaveRequestDto(hyu);
        mockMvc.perform(post("/api/hyus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(hyuSaveRequestDto))
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(hyuService).saveHyu(any());
    }

    @Test
    @DisplayName("휴 전체 게시글 목록 조회 테스트")
    void testList() throws Exception {
        List<HyuResponseDto> hyuResponseDtoList = List.of(HyuResponseDtoTest.testHyuResponseDto(hyu));
        when(hyuService.getHyuList()).thenReturn(hyuResponseDtoList);

        mockMvc.perform(get("/api/hyus/list")
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(hyuService).getHyuList();
    }
}