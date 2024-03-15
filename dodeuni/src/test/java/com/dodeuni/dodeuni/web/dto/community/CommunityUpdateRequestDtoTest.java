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
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

public class CommunityUpdateRequestDtoTest {
    private final ValidatorUtil<CommunityUpdateRequestDto> validatorUtil = new ValidatorUtil<>();

    private Community community;

    public static CommunityUpdateRequestDto testCommunityUpdateRequestDto(Community community) {
        String photoName = community.getPhotoList().get(0).getPhotoName();
        byte[] photoByteArray = community.getPhotoList().get(0).getPhotoUrl().getBytes();
        MockMultipartFile mockPhoto = new MockMultipartFile(photoName, photoByteArray);
        return CommunityUpdateRequestDto.builder()
                .communityId(community.getId())
                .userId(community.getUser().getId())
                .main(community.getMain().getMainName())
                .sub(community.getSub().getSubName())
                .title(community.getTitle())
                .content(community.getContent())
                .addPhoto(List.of(mockPhoto))
                .deletePhotoId(null)
                .build();
    }

    @BeforeEach
    void setUp() {
        community = CommunityTest.testCommunity();
        community.setUser(UserTest.testUser());
        community.setCreatedDateTime(LocalDateTime.now());
        Photo photo = PhotoTest.testPhoto();
        photo.setCommunity(community);
    }

    @Test
    @DisplayName("CommunityUpdateRequestDto 생성 테스트")
    void testCommunityUpdateRequestDtoSave() {
        CommunityUpdateRequestDto communityUpdateRequestDto = testCommunityUpdateRequestDto(community);

        assertNotNull(communityUpdateRequestDto);
        assertEquals(community.getId(), communityUpdateRequestDto.getCommunityId());
        assertEquals(community.getUser().getId(), communityUpdateRequestDto.getUserId());
        assertEquals(community.getMain().getMainName(), communityUpdateRequestDto.getMain());
        assertEquals(community.getSub().getSubName(), communityUpdateRequestDto.getSub());
        assertEquals(community.getTitle(), communityUpdateRequestDto.getTitle());
        assertEquals(community.getContent(), communityUpdateRequestDto.getContent());
        assertEquals(community.getPhotoList().get(0).getPhotoName(), communityUpdateRequestDto.getAddPhoto().get(0).getName());
        assertNull(communityUpdateRequestDto.getDeletePhotoId());
    }

    @Test
    @DisplayName("protected 기본 생성자 테스트")
    void testProtectedNoArgsConstructor() {
        CommunityUpdateRequestDto communityUpdateRequestDto = new CommunityUpdateRequestDto();

        assertNotNull(communityUpdateRequestDto);
        assertNull(communityUpdateRequestDto.getCommunityId());
        assertNull(communityUpdateRequestDto.getUserId());
        assertNull(communityUpdateRequestDto.getMain());
        assertNull(communityUpdateRequestDto.getSub());
        assertNull(communityUpdateRequestDto.getTitle());
        assertNull(communityUpdateRequestDto.getContent());
        assertNull(communityUpdateRequestDto.getAddPhoto());
        assertNull(communityUpdateRequestDto.getDeletePhotoId());
    }

    @Test
    @DisplayName("커뮤니티 게시글 아이디 유효성 검증 테스트")
    void communityId_validation() {
        CommunityUpdateRequestDto communityUpdateRequestDto = testCommunityUpdateRequestDto(community);
        communityUpdateRequestDto.setCommunityId(null);

        validatorUtil.validate(communityUpdateRequestDto);
    }

    @Test
    @DisplayName("회원 아이디 유효성 검증 테스트")
    void userId_validation() {
        CommunityUpdateRequestDto communityUpdateRequestDto = testCommunityUpdateRequestDto(community);
        communityUpdateRequestDto.setUserId(null);

        validatorUtil.validate(communityUpdateRequestDto);
    }

    @Test
    @DisplayName("카테고리 대분류 유효성 검증 테스트")
    void main_validation() {
        CommunityUpdateRequestDto communityUpdateRequestDto = testCommunityUpdateRequestDto(community);
        communityUpdateRequestDto.setMain(INVALID_EMPTY);

        validatorUtil.validate(communityUpdateRequestDto);
    }

    @Test
    @DisplayName("카테고리 소분류 유효성 검증 테스트")
    void sub_validation() {
        CommunityUpdateRequestDto communityUpdateRequestDto = testCommunityUpdateRequestDto(community);
        communityUpdateRequestDto.setSub(INVALID_EMPTY);

        validatorUtil.validate(communityUpdateRequestDto);
    }

    @Test
    @DisplayName("제목 유효성 검증 테스트")
    void title_validation() {
        CommunityUpdateRequestDto communityUpdateRequestDto = testCommunityUpdateRequestDto(community);
        communityUpdateRequestDto.setTitle(INVALID_EMPTY);

        validatorUtil.validate(communityUpdateRequestDto);
    }

    @Test
    @DisplayName("내용 유효성 검증 테스트")
    void content_validation() {
        CommunityUpdateRequestDto communityUpdateRequestDto = testCommunityUpdateRequestDto(community);
        communityUpdateRequestDto.setContent(INVALID_EMPTY);

        validatorUtil.validate(communityUpdateRequestDto);
    }
}