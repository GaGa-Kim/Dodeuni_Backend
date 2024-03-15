package com.dodeuni.dodeuni.service.community;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.dodeuni.dodeuni.config.GlobalConfig;
import com.dodeuni.dodeuni.domain.community.Photo;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AwsS3ServiceImpl implements PhotoService {
    private final AmazonS3Client amazonS3Client;
    private final GlobalConfig config;

    @Override
    public List<Photo> uploadPhotoList(List<MultipartFile> multipartFiles) {
        List<Photo> photoList = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            Photo photo = uploadPhoto(multipartFile);
            photoList.add(photo);
        }
        return photoList;
    }

    private Photo uploadPhoto(MultipartFile multipartFile) {
        String photoName = createPhotoName(multipartFile.getOriginalFilename());
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());
        try (InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3Client.putObject(new PutObjectRequest(config.getBucket(), photoName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "사진 업로드에 실패했습니다.");
        }
        return Photo.builder()
                .origPhotoName(multipartFile.getOriginalFilename())
                .photoName(photoName)
                .photoUrl(amazonS3Client.getUrl(config.getBucket(), photoName).toString())
                .build();
    }

    @Override
    public String getPhoto(String photoName) {
        return amazonS3Client.getUrl(config.getBucket(), photoName).toString();
    }

    @Override
    public void deletePhoto(String photoName) {
        DeleteObjectRequest request = new DeleteObjectRequest(config.getBucket(), photoName);
        amazonS3Client.deleteObject(request);
    }

    private String createPhotoName(String photoName) {
        return UUID.randomUUID().toString().concat(getPhotoExtension(photoName));
    }

    private String getPhotoExtension(String photoName) {
        return photoName.substring(photoName.lastIndexOf("."));
    }
}