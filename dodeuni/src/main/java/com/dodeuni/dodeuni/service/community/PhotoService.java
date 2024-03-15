package com.dodeuni.dodeuni.service.community;

import com.dodeuni.dodeuni.domain.community.Photo;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Transactional
public interface PhotoService {
    /**
     * 사진을 업로드하여 저장한다.
     *
     * @param multipartFiles (사진들)
     * @return List<Photo> (사진 목록)
     */
    List<Photo> uploadPhotoList(List<MultipartFile> multipartFiles);

    /**
     * 사진 URL을 조회한다.
     *
     * @param photoName (변환된 사진 이름)
     * @return String (사진 저장 URL)
     */
    String getPhoto(String photoName);

    /**
     * 사진을 삭제한다.
     *
     * @param photoName (변환된 사진 이름)
     */
    void deletePhoto(String photoName);
}
