package com.dodeuni.dodeuni.service;

import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.domain.community.CommunityRepository;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserRepository;
import com.dodeuni.dodeuni.web.dto.community.CommunityListResponseDto;
import com.dodeuni.dodeuni.web.dto.community.CommunityResponseDto;
import com.dodeuni.dodeuni.web.dto.community.CommunitySaveRequestDto;
import com.dodeuni.dodeuni.web.dto.community.CommunityUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;

    public CommunityResponseDto save(CommunitySaveRequestDto communitySaveRequestDto) {

        User user = findUser(communitySaveRequestDto.getUserId());
        Community community = communitySaveRequestDto.toEntity();
        community.setUser(user);
        user.addCommunityList(communityRepository.save(community));

        return new CommunityResponseDto(community);
    }

    public List<CommunityListResponseDto> getList(String main, String sub) {

        List<Community> communityList = communityRepository.findByMainAndSub(main, sub);
        return communityList.stream().map(community -> new CommunityListResponseDto(community)).collect(Collectors.toList());
    }

    public CommunityResponseDto getDetail(Long id) {

        Community community = findCommunity(id);
        return new CommunityResponseDto(community);
    }

    public CommunityResponseDto update(CommunityUpdateRequestDto communityUpdateRequestDto) {

        User user = findUser(communityUpdateRequestDto.getUserId());
        Community community = findCommunity(communityUpdateRequestDto.getId());

        if(user.equals(community.getUserId())) {
            community.update(communityUpdateRequestDto.getMain(),
                             communityUpdateRequestDto.getSub(),
                             communityUpdateRequestDto.getTitle(),
                             communityUpdateRequestDto.getContent());

            return new CommunityResponseDto(community);
        }

        else {
            throw new RuntimeException("게시글 수정에 실패했습니다.");
        }
    }

    public void delete(Long userId, Long id) {

        User user = findUser(userId);
        Community community = findCommunity(id);

        if(user.equals(community.getUserId())) {
            communityRepository.delete(community);
        }

        else {
            throw new RuntimeException("게시글 삭제에 실패했습니다.");
        }
    }

    public User findUser(Long userId) {

        return userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
    }

    public Community findCommunity(Long communityId) {

        return communityRepository.findById(communityId).orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다."));
    }
}
