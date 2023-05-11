package com.dodeuni.dodeuni.domain.community;

import com.dodeuni.dodeuni.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findByMainAndSub(String main, String sub);
    List<Community> findByUserId(User user);
}