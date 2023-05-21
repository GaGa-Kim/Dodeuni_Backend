package com.dodeuni.dodeuni.domain.user;

import com.dodeuni.dodeuni.domain.comment.Comment;
import com.dodeuni.dodeuni.domain.community.Community;
import com.dodeuni.dodeuni.domain.hyu.Hyu;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_Id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String nickname;

    private String fcmToken;

    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Community> communityList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hyu> hyuList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    public User(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

    public User updateProfile(String newNickname) {
        this.nickname = newNickname;
        return this;
    }

    public User updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
        return this;
    }

    public void addCommunityList(Community community) {
        this.communityList.add(community);
        if(community.getUserId() != this) {
            community.setUser(this);
        }
    }

    public void addHyuList(Hyu hyu){
        this.hyuList.add(hyu);
        if(hyu.getUser() != this){
            hyu.setUser(this);
        }
    }

    public void addCommentList(Comment comment){
        this.commentList.add(comment);
        if(comment.getUser() != this){
            comment.setUser(this);
        }
    }
}