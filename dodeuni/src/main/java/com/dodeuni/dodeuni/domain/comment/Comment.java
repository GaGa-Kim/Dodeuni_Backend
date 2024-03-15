package com.dodeuni.dodeuni.domain.comment;

import com.dodeuni.dodeuni.domain.BaseTime;
import com.dodeuni.dodeuni.domain.community.Community;
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
public class Comment extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommentId")
    @ApiModelProperty(notes = "댓글 아이디", dataType = "Long", example = "1")
    private Long id;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId")
    @ApiModelProperty(notes = "댓글 작성자", dataType = "User")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CommunityId")
    @ApiModelProperty(notes = "댓글을 단 커뮤니티 게시글", dataType = "Community")
    private Community community;

    @Column(nullable = false)
    @ApiModelProperty(notes = "댓글 내용", dataType = "String", example = "내용")
    private String content;

    @Column(nullable = false)
    @ApiModelProperty(notes = "댓글 계층 (댓글일 경우 0, 답댓글일 경우 1)", dataType = "Long", example = "1")
    private Long step;

    @ApiModelProperty(notes = "부모 댓글 아이디 ", dataType = "Long", example = "1")
    private Long pid;

    @Builder
    public Comment(Long id, String content, Long step, Long pid) {
        this.id = id;
        this.content = content;
        this.step = step;
        this.pid = pid;
    }

    public void setCommunity(Community community) {
        this.community = community;
        if (!community.getCommentList().contains(this)) {
            community.getCommentList().add(this);
        }
    }

    public void updateComment(String content) {
        this.content = content;
    }
}
