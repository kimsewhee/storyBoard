package org.iclass.story.dto;

import lombok.Builder;
import org.iclass.story.domain.Story;

import java.time.LocalDateTime;

@Builder
public record StoryDto(
        Long id,
        UserrAccountDto userrAccountDto,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime modifiedAt,
        String modifiedBy
)  {
    public static StoryDto of(Long id, UserrAccountDto userrAccountDto, String title, String content, String hashtag,
                              LocalDateTime createdAt,String createdBy,LocalDateTime modifiedAt, String modifiedBy) {
        return new StoryDto(id,userrAccountDto,title,content,hashtag,createdAt,createdBy,modifiedAt,modifiedBy);
    }

    public static StoryDto from (Story entity){
        return new StoryDto(entity.getId(),
                UserrAccountDto.from(entity.getUserAccount()),
                entity.getTitle(),
                entity.getContent(),
                entity.getHashtag(),
                entity.getCreatedAt(),
                entity.getCreatedBy(),
                entity.getModifiedAt(),
                entity.getModifiedBy());
    }

    public Story toEntity(){
        return Story.of(userrAccountDto.toEntity(),
                title,
                content,
                hashtag
        );
    }
}
