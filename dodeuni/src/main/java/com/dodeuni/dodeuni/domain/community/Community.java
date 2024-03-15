package com.dodeuni.dodeuni.domain.community;

import com.dodeuni.dodeuni.domain.BaseTime;
import com.dodeuni.dodeuni.domain.comment.Comment;
import com.dodeuni.dodeuni.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class Community extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommunityId")
    @ApiModelProperty(notes = "커뮤니티 게시글 아이디", dataType = "Long", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId")
    @ApiModelProperty(notes = "게시글 작성자", dataType = "User")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @ApiModelProperty(notes = "커뮤니티 카테고리 대분류", dataType = "Main", example = "정보")
    private Main main;

    @Enumerated(EnumType.STRING)
    @ApiModelProperty(notes = "커뮤니티 카테고리 소분류", dataType = "Sub", example = "정보교환")
    private Sub sub;

    @Column(nullable = false)
    @ApiModelProperty(notes = "커뮤니티 게시글 제목", dataType = "String", example = "제목")
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    @ApiModelProperty(notes = "커뮤니티 게시글 내용", dataType = "String", example = "내용")
    private String content;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    @ApiModelProperty(notes = "커뮤니티 게시글 사진 목록", dataType = "List<Photo>")
    private final List<Photo> photoList = new ArrayList<>();

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL, orphanRemoval = true)
    @ApiModelProperty(notes = "커뮤니티 게시글 댓글 목록", dataType = "List<Photo>")
    private final List<Comment> commentList = new ArrayList<>();

    @Builder
    public Community(Long id, String main, String sub, String title, String content) {
        this.id = id;
        this.main = Main.findMainName(main);
        this.sub = Sub.findSubName(sub);
        this.title = title;
        this.content = content;
    }

    public void setUser(User user) {
        this.user = user;
        if (!user.getCommunityList().contains(this)) {
            user.getCommunityList().add(this);
        }
    }

    public void update(String main, String sub, String title, String content) {
        this.main = Main.findMainName(main);
        this.sub = Sub.findSubName(sub);
        this.title = title;
        this.content = content;
    }
}