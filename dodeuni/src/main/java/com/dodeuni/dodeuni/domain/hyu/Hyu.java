package com.dodeuni.dodeuni.domain.hyu;

import com.dodeuni.dodeuni.domain.BaseTime;
import com.dodeuni.dodeuni.domain.user.User;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Entity
public class Hyu extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "HyuId")
    @ApiModelProperty(notes = "휴 아이디", dataType = "Long", example = "1")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId")
    @ApiModelProperty(notes = "휴 작성자", dataType = "User")
    private User user;

    @Column(nullable = false)
    @ApiModelProperty(notes = "휴 내용", dataType = "String", example = "내용")
    private String content;

    @Builder
    public Hyu(Long id, String content) {
        this.id = id;
        this.content = content;
    }
}
