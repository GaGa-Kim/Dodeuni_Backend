package com.dodeuni.dodeuni.domain.community;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findByMainAndSub(Main main, Sub sub);
}