package com.dodeuni.dodeuni.domain.comment;

import com.dodeuni.dodeuni.domain.community.Community;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByCommunityOrderByCreatedDateTimeAsc(Community community);
    List<Comment> findByCommunity(Community community);
}
