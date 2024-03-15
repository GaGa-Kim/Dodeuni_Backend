package com.dodeuni.dodeuni.domain.comment;

import com.dodeuni.dodeuni.domain.community.Community;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByCommunityOrderByCreatedDateTimeAsc(Community community);

    List<Comment> findAllByCommunityInOrderByCreatedDateTimeDesc(List<Community> communityList);
}