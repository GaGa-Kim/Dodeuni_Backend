package com.dodeuni.dodeuni.domain.comment;

import com.dodeuni.dodeuni.domain.BaseTime;
import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CommentId")
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Long step;

    private Long pid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Community_Id")
    private Community community;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    private User user;

    @Builder
    public Comment(String content, Long step, Long pid){
        this.content=content;
        this.step=step;
        this.pid=pid;
    }

    public void setCommunity(Community community){
        this.community=community;
        if(!community.getCommentList().contains(this))
            community.getCommentList().add(this);
    }

    public void setUser(User user) {
        this.user = user;
        if(!user.getCommentList().contains(this))
            user.getCommentList().add(this);
    }

    public Comment updateComment(String content){
        this.content=content;
        return this;
    }
}
