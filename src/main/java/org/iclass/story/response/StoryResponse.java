package org.iclass.story.response;

import org.iclass.story.dto.StoryDto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record StoryResponse(
        Long id,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String email,
        String nickname
) implements Serializable {

    public static StoryResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt, String email, String nickname) {
        return new StoryResponse(id, title, content, hashtag, createdAt, email, nickname);
    }


    public static StoryResponse from(StoryDto dto){
        String nickname = dto.userrAccountDto().nickname();
        if(nickname==null || nickname.isBlank()) {
            nickname=dto.userrAccountDto().userId();
        }

        return new StoryResponse(dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtag(),
                dto.createdAt(),
                dto.userrAccountDto().email(),
                nickname);
    }
}
