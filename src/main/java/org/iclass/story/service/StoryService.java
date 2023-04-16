package org.iclass.story.service;

import lombok.RequiredArgsConstructor;
import org.iclass.story.dto.StoryDto;
import org.iclass.story.dto.StoryUpdateDto;
import org.iclass.story.repository.StoryRepository;
import org.iclass.story.type.SearchType;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class StoryService {

    private final StoryRepository storyRepository;
    @Transactional(readOnly = true)
    public Page<StoryDto> searhStories(SearchType title, String searchKeyword) {
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public StoryDto searchStory(long storyId){
        return null;
    }

    public void saveStory(StoryDto dto){

    }

    public void updateStory(long storyId, StoryUpdateDto dto){

    }

    public void deleteStory(long storyId){

    }

}
