package org.iclass.story.dto;

import org.iclass.story.domain.Story;
import org.iclass.story.domain.StoryComment;

import java.time.LocalDateTime;

public record StoryCommentDto (
        Long id,
        Long storyId,
        UserrAccountDto userrAccountDto,
        String content,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
){
    public static StoryCommentDto of(Long id, Long articleId, UserrAccountDto
            userrAccountDto,String content, LocalDateTime createdAt,String createdBy , LocalDateTime modifiedAt, String modifiedBy){
        return new StoryCommentDto(id,articleId,userrAccountDto,  content, createdAt, createdBy, modifiedAt, modifiedBy);
    }

    public static StoryCommentDto from (StoryComment entity){
        return new StoryCommentDto(entity.getId(),
                entity.getStory().getId(),
                UserrAccountDto.from(entity.getUserAccount()),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy());
    }

    public StoryComment toEntity(Story entity){
        return StoryComment.of(
                entity,
                userrAccountDto.toEntity(),
                content
        );
    }
}
