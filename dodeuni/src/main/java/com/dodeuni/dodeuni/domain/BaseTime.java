package com.dodeuni.dodeuni.domain;

import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTime {
    @CreatedDate
    @ApiModelProperty(notes = "생성 날짜", dataType = "LocalDateTime", example = "20XX-11-XXT11:44:30.327959")
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @ApiModelProperty(notes = "수정 날짜", dataType = "LocalDateTime", example = "20XX-XX-XXT11:44:30.327959")
    private LocalDateTime modifiedDateTime;
}