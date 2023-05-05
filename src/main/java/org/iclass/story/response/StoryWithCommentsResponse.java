package org.iclass.story.response;

import org.iclass.story.dto.StoryWithCommentsDto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public record StoryWithCommentsResponse(
        Long id,
        String title,
        String content,
        String hashtag,
        LocalDateTime createdAt,
        String email,
        String nickname,
        Set<StoryCommentResponse> storyCommentsResponse
) implements Serializable {

    public static StoryWithCommentsResponse of(Long id, String title, String content, String hashtag, LocalDateTime createdAt,
                                               String email, String nickname, Set<StoryCommentResponse> storyCommentResponses) {
        return new StoryWithCommentsResponse(id, title, content, hashtag, createdAt, email, nickname,storyCommentResponses);
    }


    public static StoryWithCommentsResponse from(StoryWithCommentsDto dto){
        String nickname = dto.userrAccountDto().nickname();
        if(nickname==null || nickname.isBlank()) {
            nickname=dto.userrAccountDto().userId();
        }

        return new StoryWithCommentsResponse(dto.id(),
                dto.title(),
                dto.content(),
                dto.hashtag(),
                dto.createdAt(),
                dto.userrAccountDto().email(),
                nickname,
                dto.storyCommentDtos().stream()
                        .map(StoryCommentResponse::from)
                        .collect(Collectors.toCollection(LinkedHashSet::new)));
    }
}
