package com.dodeuni.dodeuni.web.dto.community;

import static com.dodeuni.dodeuni.web.dto.auth.UserSaveRequestDtoTest.INVALID_EMPTY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.domain.community.CommunityTest;
import com.dodeuni.dodeuni.domain.community.Photo;
import com.dodeuni.dodeuni.domain.community.PhotoTest;
import com.dodeuni.dodeuni.domain.user.UserTest;
import com.dodeuni.dodeuni.web.dto.ValidatorUtil;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

public class CommunitySaveRequestDtoTest {
    private final ValidatorUtil<CommunitySaveRequestDto> validatorUtil = new ValidatorUtil<>();

    private Community community;

    public static CommunitySaveRequestDto testCommunitySaveRequestDto(Community community) {
        String photoName = community.getPhotoList().get(0).getPhotoName();
        byte[] photoByteArray = community.getPhotoList().get(0).getPhotoUrl().getBytes();
        MockMultipartFile mockPhoto = new MockMultipartFile(photoName, photoByteArray);
        return CommunitySaveRequestDto.builder()
                .userId(community.getUser().getId())
                .main(community.getMain().getMainName())
                .sub(community.getSub().getSubName())
                .title(community.getTitle())
                .content(community.getContent())
                .photos(List.of(mockPhoto))
                .build();
    }

    @BeforeEach
    void setUp() {
        community = CommunityTest.testCommunity();
        community.setUser(UserTest.testUser());
        Photo photo = PhotoTest.testPhoto();
        photo.setCommunity(community);
    }

    @Test
    @DisplayName("CommunitySaveRequestDto 생성 테스트")
    void testCommunitySaveRequestDtoSave() {
        CommunitySaveRequestDto communitySaveRequestDto = testCommunitySaveRequestDto(community);

        assertNotNull(communitySaveRequestDto);
        assertEquals(community.getUser().getId(), communitySaveRequestDto.getUserId());
        assertEquals(community.getMain().getMainName(), communitySaveRequestDto.getMain());
        assertEquals(community.getSub().getSubName(), communitySaveRequestDto.getSub());
        assertEquals(community.getTitle(), communitySaveRequestDto.getTitle());
        assertEquals(community.getContent(), communitySaveRequestDto.getContent());
        assertEquals(community.getPhotoList().get(0).getPhotoName(), communitySaveRequestDto.getPhotos().get(0).getName());
    }

    @Test
    @DisplayName("CommunitySaveRequestDto toEntity 테스트")
    void testCommunitySaveRequestDtoToEntity() {
        CommunitySaveRequestDto communitySaveRequestDto = testCommunitySaveRequestDto(community);

        Community communityEntity = communitySaveRequestDto.toEntity();
        communityEntity.setUser(community.getUser());
        Photo photo = PhotoTest.testPhoto();
        photo.setCommunity(communityEntity);

        assertNotNull(communitySaveRequestDto);
        assertEquals(communitySaveRequestDto.getUserId(), communityEntity.getUser().getId());
        assertEquals(communitySaveRequestDto.getMain(), communityEntity.getMain().getMainName());
        assertEquals(communitySaveRequestDto.getSub(), communityEntity.getSub().getSubName());
        assertEquals(communitySaveRequestDto.getTitle(), communityEntity.getTitle());
        assertEquals(communitySaveRequestDto.getContent(), communityEntity.getContent());
        assertEquals(communitySaveRequestDto.getPhotos().get(0).getName(), communityEntity.getPhotoList().get(0).getPhotoName());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        CommunitySaveRequestDto communitySaveRequestDto = new CommunitySaveRequestDto();

        assertNotNull(communitySaveRequestDto);
        assertNull(communitySaveRequestDto.getUserId());
        assertNull(communitySaveRequestDto.getMain());
        assertNull(communitySaveRequestDto.getSub());
        assertNull(communitySaveRequestDto.getTitle());
        assertNull(communitySaveRequestDto.getContent());
        assertNull(communitySaveRequestDto.getPhotos());
    }

    @Test
    @DisplayName("회원 아이디 유효성 검증 테스트")
    void userId_validation() {
        CommunitySaveRequestDto communitySaveRequestDto = testCommunitySaveRequestDto(community);
        communitySaveRequestDto.setUserId(null);

        validatorUtil.validate(communitySaveRequestDto);
    }

    @Test
    @DisplayName("카테고리 대분류 유효성 검증 테스트")
    void main_validation() {
        CommunitySaveRequestDto communitySaveRequestDto = testCommunitySaveRequestDto(community);
        communitySaveRequestDto.setContent(INVALID_EMPTY);

        validatorUtil.validate(communitySaveRequestDto);
    }

    @Test
    @DisplayName("카테고리 소분류 유효성 검증 테스트")
    void sub_validation() {
        CommunitySaveRequestDto communitySaveRequestDto = testCommunitySaveRequestDto(community);
        communitySaveRequestDto.setSub(INVALID_EMPTY);

        validatorUtil.validate(communitySaveRequestDto);
    }

    @Test
    @DisplayName("제목 유효성 검증 테스트")
    void title_validation() {
        CommunitySaveRequestDto communitySaveRequestDto = testCommunitySaveRequestDto(community);
        communitySaveRequestDto.setTitle(INVALID_EMPTY);

        validatorUtil.validate(communitySaveRequestDto);
    }

    @Test
    @DisplayName("내용 유효성 검증 테스트")
    void content_validation() {
        CommunitySaveRequestDto communitySaveRequestDto = testCommunitySaveRequestDto(community);
        communitySaveRequestDto.setContent(INVALID_EMPTY);

        validatorUtil.validate(communitySaveRequestDto);
    }
}