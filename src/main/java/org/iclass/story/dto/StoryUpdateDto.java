package org.iclass.story.dto;

public record StoryUpdateDto(
        String title,
        String content,
        String hashtag
) {
    public static StoryUpdateDto of(String title,String content,String hashtag){
        return new StoryUpdateDto(title, content, hashtag);
    }
}
