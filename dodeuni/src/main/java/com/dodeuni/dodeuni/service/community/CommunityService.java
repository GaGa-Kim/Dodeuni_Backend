package com.dodeuni.dodeuni.service.community;

import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.web.dto.community.CommunityListResponseDto;
import com.dodeuni.dodeuni.web.dto.community.CommunityResponseDto;
import com.dodeuni.dodeuni.web.dto.community.CommunitySaveRequestDto;
import com.dodeuni.dodeuni.web.dto.community.CommunityUpdateRequestDto;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CommunityService {
    /**
     * 커뮤니티 게시글을 저장한다.
     *
     * @param requestDto (커뮤니티 게시글 저장 정보를 담은 DTO)
     * @return CommunityResponseDto (커뮤니티 게시글 정보를 담은 DTO)
     */
    CommunityResponseDto saveCommunity(CommunitySaveRequestDto requestDto);

    /**
     * 커뮤니티 카테고리 대분류와 소분류에 따른 게시글 목록을 조회한다.
     *
     * @param main (카테고리 대분류)
     * @param sub  (카테고리 소분류)
     * @return List<CommunityListResponseDto> (커뮤니티 게시글 목록 정보를 담은 DTO)
     */
    @Transactional(readOnly = true)
    List<CommunityListResponseDto> getCommunityList(String main, String sub);

    /**
     * 커뮤니티 게시글을 조회한다.
     *
     * @param communityId (커뮤니티 아이디)
     * @return Community (커뮤니티 게시글)
     */
    @Transactional(readOnly = true)
    Community findByCommunityId(Long communityId);

    /**
     * 커뮤니티 게시글을 상세 조회한다.
     *
     * @param communityId (커뮤니티 아이디)
     * @return CommunityResponseDto (커뮤니티 게시글 정보를 담은 DTO)
     */
    @Transactional(readOnly = true)
    CommunityResponseDto getCommunity(Long communityId);

    /**
     * 커뮤니티 게시글을 수정한다.
     *
     * @param requestDto (커뮤니티 게시글 수정 정보를 담은 DTO)
     * @return CommunityResponseDto (커뮤니티 게시글 정보를 담은 DTO)
     */
    CommunityResponseDto updateCommunity(CommunityUpdateRequestDto requestDto);

    /**
     * 커뮤니티 게시글을 삭제한다.
     *
     * @param communityId (커뮤니티 아이디)
     */
    void deleteCommunity(Long communityId);
}
