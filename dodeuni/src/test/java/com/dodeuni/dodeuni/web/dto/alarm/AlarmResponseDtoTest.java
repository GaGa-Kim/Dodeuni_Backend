package com.dodeuni.dodeuni.web.dto.alarm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.dodeuni.dodeuni.domain.comment.Comment;
import com.dodeuni.dodeuni.domain.comment.CommentTest;
import com.dodeuni.dodeuni.domain.community.CommunityTest;
import com.dodeuni.dodeuni.domain.user.UserTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AlarmResponseDtoTest {
    private Comment comment;

    public static AlarmResponseDto testAlarmResponseDto(Comment comment) {
        return new AlarmResponseDto(comment);
    }

    @BeforeEach
    void setUp() {
        comment = CommentTest.testComment();
        comment.setUser(UserTest.testUser());
        comment.setCommunity(CommunityTest.testCommunity());
    }

    @Test
    @DisplayName("AlarmResponseDto 생성 테스트")
    void testAlarmResponseDtoSave() {
        AlarmResponseDto alarmResponseDto = testAlarmResponseDto(comment);

        assertNotNull(alarmResponseDto);
        assertEquals(comment.getId(), alarmResponseDto.getCommunityId());
        assertEquals(comment.getCommunity().getMain().getMainName(), alarmResponseDto.getMain());
        assertEquals(comment.getCommunity().getSub().getSubName(), alarmResponseDto.getSub());
        assertEquals("0초 전", alarmResponseDto.getCreatedDateTime());
        assertTrue(alarmResponseDto.getAlarm().contains(comment.getUser().getNickname()));
    }
}