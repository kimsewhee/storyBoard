package org.iclass.story.response;

import org.iclass.story.dto.StoryCommentDto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record StoryCommentResponse(
        Long id,
        String content,
        LocalDateTime createdAt,
        String email,
        String nickname

) implements Serializable {     //Dto 역할이나 필요한 데이터만 추출함.

    public static StoryCommentResponse of (Long id, String content, LocalDateTime createdAt , String email , String nickname){
        return new StoryCommentResponse(id, content, createdAt, email, nickname);
    }

    public static StoryCommentResponse from(StoryCommentDto dto){
        String nickname = dto.userrAccountDto().nickname();
        if(nickname==null || nickname.isBlank()) {
            nickname=dto.userrAccountDto().userId();
        }

        return new StoryCommentResponse(dto.id(),
                dto.content(),
                dto.createdAt(),
                dto.userrAccountDto().email(),
                nickname);
    }
}
//Controller 는 Response dto 만 의존성을 갖고
//Service 는  domain 과 dto 의 의존성을 갖도록 한다.
