package com.dodeuni.dodeuni.domain.community;

import com.dodeuni.dodeuni.domain.user.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityRepository extends JpaRepository<Community, Long> {
    List<Community> findByMainAndSub(Main main, Sub sub);

    List<Community> findByUser(User user);
}