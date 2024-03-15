package com.dodeuni.dodeuni.service.community;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.amazonaws.services.s3.AmazonS3Client;
import com.dodeuni.dodeuni.config.GlobalConfig;
import com.dodeuni.dodeuni.domain.community.Photo;
import com.dodeuni.dodeuni.domain.community.PhotoTest;
import io.findify.s3mock.S3Mock;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
@Import(AwsS3MockConfig.class)
class AwsS3ServiceImplTest {
    private Photo photo;
    private List<MultipartFile> files;

    @Autowired
    private AwsS3ServiceImpl awsS3Service;

    @BeforeEach
    void setUp() {
        photo = PhotoTest.testPhoto();
        MockMultipartFile mockMultipartFile = new MockMultipartFile(photo.getPhotoName(), photo.getOrigPhotoName(), "image/png", photo.getPhotoUrl().getBytes());
        files = List.of(mockMultipartFile);
    }

    @BeforeAll
    static void setUp(@Autowired S3Mock s3Mock,
                      @Autowired AmazonS3Client amazonS3Client,
                      @Autowired GlobalConfig config) {
        s3Mock.start();
        amazonS3Client.createBucket(config.getBucket());
    }

    @AfterAll
    static void tearDown(@Autowired S3Mock s3Mock,
                         @Autowired AmazonS3Client amazonS3Client) {
        amazonS3Client.shutdown();
        s3Mock.stop();
    }

    @Test
    @DisplayName("사진 업로드 저장 테스트")
    void testUploadPhotoList() {
        List<Photo> result = awsS3Service.uploadPhotoList(files);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(photo.getOrigPhotoName(), result.get(0).getOrigPhotoName());
    }

    @Test
    @DisplayName("사진 업로드 후, 사진 URL 조회 테스트")
    void testGetPhoto() {
        String newFileName = awsS3Service.uploadPhotoList(files).get(0).getPhotoName();

        String result = awsS3Service.getPhoto(newFileName);

        assertNotNull(result);
    }

    @Test
    @DisplayName("사진 업로드 후, 사진 삭제 테스트")
    void testDeletePhoto() {
        String newFileName = awsS3Service.uploadPhotoList(files).get(0).getPhotoName();

        awsS3Service.deletePhoto(newFileName);
    }
}