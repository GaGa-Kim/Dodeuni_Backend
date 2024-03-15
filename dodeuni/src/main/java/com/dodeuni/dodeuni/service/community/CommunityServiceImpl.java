package com.dodeuni.dodeuni.service.community;

import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.domain.community.CommunityRepository;
import com.dodeuni.dodeuni.domain.community.Main;
import com.dodeuni.dodeuni.domain.community.Photo;
import com.dodeuni.dodeuni.domain.community.PhotoRepository;
import com.dodeuni.dodeuni.domain.community.Sub;
import com.dodeuni.dodeuni.domain.user.User;
import com.dodeuni.dodeuni.except.NotFoundException;
import com.dodeuni.dodeuni.service.user.UserService;
import com.dodeuni.dodeuni.web.dto.community.CommunityListResponseDto;
import com.dodeuni.dodeuni.web.dto.community.CommunityResponseDto;
import com.dodeuni.dodeuni.web.dto.community.CommunitySaveRequestDto;
import com.dodeuni.dodeuni.web.dto.community.CommunityUpdateRequestDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CommunityServiceImpl implements CommunityService {
    private final CommunityRepository communityRepository;
    private final PhotoRepository photoRepository;
    private final UserService userService;
    private final PhotoService photoService;

    @Override
    public CommunityResponseDto saveCommunity(CommunitySaveRequestDto requestDto) {
        User user = userService.findByUserId(requestDto.getUserId());
        Community community = createCommunity(user, requestDto);
        if (!CollectionUtils.isEmpty(requestDto.getPhotos())) {
            addPhoto(community, requestDto.getPhotos());
        }
        return new CommunityResponseDto(community);
    }

    @Override
    public List<CommunityListResponseDto> getCommunityList(String main, String sub) {
        Main mainCategory = Main.findMainName(main);
        Sub subCategory = Sub.findSubName(sub);
        List<Community> communityList = communityRepository.findByMainAndSub(mainCategory, subCategory);
        return communityList.
                stream().
                map(CommunityListResponseDto::new).
                collect(Collectors.toList());
    }

    @Override
    public Community findByCommunityId(Long communityId) {
        return communityRepository.findById(communityId).orElseThrow(NotFoundException::new);
    }

    @Override
    public CommunityResponseDto getCommunity(Long communityId) {
        Community community = findByCommunityId(communityId);
        return new CommunityResponseDto(community);
    }

    @Override
    public CommunityResponseDto updateCommunity(CommunityUpdateRequestDto requestDto) {
        List<Long> deletePhotoIds = requestDto.getDeletePhotoId();
        if (!CollectionUtils.isEmpty(deletePhotoIds)) {
            deletePhoto(requestDto.getDeletePhotoId());
        }
        Community community = modifyCommunity(requestDto);
        List<MultipartFile> addPhotos = requestDto.getAddPhoto();
        if (!CollectionUtils.isEmpty(addPhotos)) {
            addPhoto(community, requestDto.getAddPhoto());
        }
        return new CommunityResponseDto(community);
    }

    @Override
    public void deleteCommunity(Long communityId) {
        Community community = findByCommunityId(communityId);
        List<Long> deletePhotoIdList = community.getPhotoList()
                .stream()
                .map(Photo::getId)
                .collect(Collectors.toList());
        deletePhoto(deletePhotoIdList);
        communityRepository.delete(community);
    }

    private Community createCommunity(User user, CommunitySaveRequestDto requestDto) {
        Community community = requestDto.toEntity();
        community.setUser(user);
        communityRepository.save(community);
        return community;
    }

    private void addPhoto(Community community, List<MultipartFile> photos) {
        List<Photo> photoList = photoService.uploadPhotoList(photos);
        if (!photoList.isEmpty()) {
            for (Photo photo : photoList) {
                photoRepository.save(photo);
                photo.setCommunity(community);
            }
        }
    }

    private Community modifyCommunity(CommunityUpdateRequestDto requestDto) {
        Community community = findByCommunityId(requestDto.getCommunityId());
        community.update(requestDto.getMain(),
                requestDto.getSub(),
                requestDto.getTitle(),
                requestDto.getContent());
        return community;
    }

    private void deletePhoto(List<Long> deletePhotoIdList) {
        for (Long deletePhotoId : deletePhotoIdList) {
            Photo photo = findByPhotoId(deletePhotoId);
            photoService.deletePhoto(photo.getPhotoName());
            photoRepository.delete(photo);
        }
    }

    private Photo findByPhotoId(Long photoId) {
        return photoRepository.findById(photoId).orElseThrow(NotFoundException::new);
    }
}
