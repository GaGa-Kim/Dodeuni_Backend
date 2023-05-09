package com.dodeuni.dodeuni.domain.hyu;

import com.dodeuni.dodeuni.domain.BaseTime;
import com.dodeuni.dodeuni.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Hyu extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="HyuId")
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_Id")
    private User user;

    @Builder
    public Hyu(String content){
        this.content=content;
    }

    public void setUser(User user) {
        this.user = user;
        if(!user.getHyuList().contains(this))
            user.getHyuList().add(this);
    }
}
