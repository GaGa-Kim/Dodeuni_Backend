package com.dodeuni.dodeuni.domain.community;

import com.dodeuni.dodeuni.domain.BaseTime;
import com.dodeuni.dodeuni.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Community extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Community_Id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    private User userId;

    @Column(nullable = false)
    private String main;

    @Column(nullable = false)
    private String sub;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder
    public Community(Long id, String main, String sub, String title, String content) {
        this.id = id;
        this.main = main;
        this.sub = sub;
        this.title = title;
        this.content = content;
    }

    public void setUser(User user) {
        this.userId = user;
        if(!userId.getCommunityList().contains(this))
            user.getCommunityList().add(this);
    }
}