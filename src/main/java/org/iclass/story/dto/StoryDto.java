package org.iclass.story.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record StoryDto(
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy
)  {
    public static StoryDto of(String title, String content, String hashtag, LocalDateTime now,String createdBy) {
        return new StoryDto(title,content,hashtag,now,createdBy);
    }
}
