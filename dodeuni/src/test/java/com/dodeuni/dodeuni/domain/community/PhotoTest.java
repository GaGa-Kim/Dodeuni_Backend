package com.dodeuni.dodeuni.domain.community;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PhotoTest {
    public static final Long PHOTO_ID = 1L;
    public static final String PHOTO_ORIG_NAME = "사진 원본 이름";
    public static final String PHOTO_NAME = "변환된 사진 이름";
    public static final String PHOTO_URL = "www.google.com";

    public static Photo testPhoto() {
        return Photo.builder()
                .id(PHOTO_ID)
                .origPhotoName(PHOTO_ORIG_NAME)
                .photoName(PHOTO_NAME)
                .photoUrl(PHOTO_URL)
                .build();
    }

    private Photo photo;

    @BeforeEach
    void setUp() {
        photo = testPhoto();
    }

    @Test
    @DisplayName("사진 추가 테스트")
    void testPhotoSave() {
        assertNotNull(photo);
        assertEquals(PHOTO_ID, photo.getId());
        assertEquals(PHOTO_ORIG_NAME, photo.getOrigPhotoName());
        assertEquals(PHOTO_NAME, photo.getPhotoName());
        assertEquals(PHOTO_URL, photo.getPhotoUrl());
    }

    @Test
    @DisplayName("사진의 커뮤니티 연관관계 설정 테스트")
    void testSetCommunity() {
        Community community = mock(Community.class);
        photo.setCommunity(community);

        assertEquals(community, photo.getCommunity());
    }
}