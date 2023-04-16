package org.iclass.story.dto;

import java.time.LocalDateTime;

public record StoryCommentDto (
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy,
        String content
){
    public static StoryCommentDto of(LocalDateTime createdAt,String createdBy , LocalDateTime modifiedAt, String modifiedBy,String content){
        return new StoryCommentDto(createdAt, createdBy, modifiedAt, modifiedBy, content);
    }
}
