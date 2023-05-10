package com.dodeuni.dodeuni.domain.community;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Long> {
    List<Photo> findAllByCommunityId(Community community);
}
