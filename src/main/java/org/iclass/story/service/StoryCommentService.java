package org.iclass.story.service;

import lombok.RequiredArgsConstructor;
import org.iclass.story.dto.StoryCommentDto;
import org.iclass.story.repository.StoryCommentRepository;
import org.iclass.story.repository.StoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class StoryCommentService {

    private final StoryRepository storyRepository;
    private final StoryCommentRepository storyCommentRepository;

    @Transactional(readOnly = true)
    public List<StoryCommentDto> searchStroyComments(Long storyId){
        return List.of();
    }

    public void saveStoryComment(StoryCommentDto dto){

    }

    public void updateStoryComment(StoryCommentDto dto){

    }

    public void deleteStoryComment(Long storyCommentId){

    }
}
