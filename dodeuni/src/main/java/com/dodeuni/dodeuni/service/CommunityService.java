package com.dodeuni.dodeuni.service;

import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.domain.community.CommunityRepository;
import com.dodeuni.dodeuni.domain.community.Photo;
import com.dodeuni.dodeuni.domain.community.PhotoRepository;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.domain.user.UserRepository;
import com.dodeuni.dodeuni.web.dto.community.CommunityListResponseDto;
import com.dodeuni.dodeuni.web.dto.community.CommunityResponseDto;
import com.dodeuni.dodeuni.web.dto.community.CommunitySaveRequestDto;
import com.dodeuni.dodeuni.web.dto.community.CommunityUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CommunityService {

    private final CommunityRepository communityRepository;
    private final UserRepository userRepository;
    private final AwsS3Service awsS3Service;
    private final PhotoRepository photoRepository;

    public CommunityResponseDto save(CommunitySaveRequestDto communitySaveRequestDto) {

        User user = userRepository.findById(communitySaveRequestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        Community community = communitySaveRequestDto.toEntity();
        community.setUser(user);
        user.addCommunityList(communityRepository.save(community));

        if(communitySaveRequestDto.getPhoto() != null) {
            addPhoto(community, communitySaveRequestDto.getPhoto());
        }

        return new CommunityResponseDto(community);
    }

    public List<CommunityListResponseDto> getList(String main, String sub) {

        List<Community> communityList = communityRepository.findByMainAndSub(main, sub);
        return communityList.stream().map(CommunityListResponseDto::new).collect(Collectors.toList());
    }

    public CommunityResponseDto getDetail(Long id) {

        Community community = communityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다."));
        return new CommunityResponseDto(community);
    }

    public CommunityResponseDto update(CommunityUpdateRequestDto communityUpdateRequestDto) {

        User user = userRepository.findById(communityUpdateRequestDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        Community community = communityRepository.findById(communityUpdateRequestDto.getId()).orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다."));

        if(user.equals(community.getUserId())) {
            community.update(communityUpdateRequestDto.getMain(),
                             communityUpdateRequestDto.getSub(),
                             communityUpdateRequestDto.getTitle(),
                             communityUpdateRequestDto.getContent());

            if(!CollectionUtils.isEmpty(communityUpdateRequestDto.getAddPhoto())) {
                addPhoto(community, communityUpdateRequestDto.getAddPhoto());
            }

            return new CommunityResponseDto(community);
        }

        else {
            throw new RuntimeException("게시글 수정에 실패했습니다.");
        }
    }

    public void delete(Long userId, Long id) {

        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));
        Community community = communityRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다."));
        List<Photo> photoList = photoRepository.findAllByCommunityId(community);

        if(user.equals(community.getUserId())) {
            deletePhoto(photoList.stream().map(Photo::getId).collect(Collectors.toList()));
            communityRepository.delete(community);
        }

        else {
            throw new RuntimeException("게시글 삭제에 실패했습니다.");
        }
    }

    public void addPhoto(Community community, List<MultipartFile> photos) {

        List<Photo> photoList = awsS3Service.uploadPhoto(photos);
        if(!photoList.isEmpty()) {
            for(Photo photo: photoList) {
                community.addPhotoList(photoRepository.save(photo));
            }
        }
    }

    public void deletePhoto(List<Long> deletePhotoId) {

        for(Long deleteId: deletePhotoId) {
            Photo photo = photoRepository.findById(deleteId).orElseThrow(() -> new IllegalArgumentException("해당 사진이 없습니다."));
            awsS3Service.deleteS3(photo.getPhotoName());
            photoRepository.delete(photo);
        }
    }
}
