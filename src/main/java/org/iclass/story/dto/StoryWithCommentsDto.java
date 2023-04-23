package org.iclass.story.dto;

import lombok.Builder;
import org.iclass.story.domain.Story;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record StoryWithCommentsDto(
        Long id,
        UserrAccountDto userrAccountDto,
        Set<StoryCommentDto> storyCommentDtos,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
)  {
    public static StoryWithCommentsDto of(Long id, UserrAccountDto userrAccountDto, Set<StoryCommentDto> storyCommentDtos,String title, String content, String hashtag,
                                          LocalDateTime createdAt, String createdBy, LocalDateTime modifiedAt, String modifiedBy) {
        return new StoryWithCommentsDto(id,userrAccountDto,storyCommentDtos,title,content,hashtag,createdAt,createdBy,modifiedAt,modifiedBy);
    }

    public static StoryWithCommentsDto from (Story entity){
        return new StoryWithCommentsDto(entity.getId(),
                UserrAccountDto.from(entity.getUserAccount()),
                entity.getStoryComments().stream()
                                .map(StoryCommentDto::from)
                                        .collect(Collectors.toCollection(LinkedHashSet::new)),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy());
    }

}
