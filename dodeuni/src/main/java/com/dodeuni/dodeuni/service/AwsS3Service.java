package com.dodeuni.dodeuni.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import com.dodeuni.dodeuni.domain.community.Photo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AwsS3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    public String bucket;

    public List<Photo> uploadPhoto(List<MultipartFile> multipartFiles) {
        List<Photo> photoList = new ArrayList<>();

        for(MultipartFile multipartFile: multipartFiles) {

            String photoName = createPhotoName(multipartFile.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            try(InputStream inputStream = multipartFile.getInputStream()) {
                amazonS3Client.putObject(new PutObjectRequest(bucket, photoName, inputStream, objectMetadata).withCannedAcl(CannedAccessControlList.PublicRead));
            } catch(IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "사진 업로드에 실패했습니다.");
            }

            Photo photo = new Photo(multipartFile.getOriginalFilename(), photoName, amazonS3Client.getUrl(bucket, photoName).toString());
            photoList.add(photo);
        }
        return photoList;
    }

    public String getS3(String photoName) {
        return amazonS3Client.getUrl(bucket, photoName).toString();
    }

    public void deleteS3(String photoName) {
        DeleteObjectRequest request = new DeleteObjectRequest(bucket, photoName);
        amazonS3Client.deleteObject(request);
    }

    public String createPhotoName(String photoName) {
        return UUID.randomUUID().toString().concat(getPhotoExtension(photoName));
    }

    public String getPhotoExtension(String photoName) {
        try {
            return photoName.substring(photoName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 사진(" + photoName + ") 입니다.");
        }
    }
}