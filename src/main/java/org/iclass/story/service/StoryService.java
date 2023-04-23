package org.iclass.story.service;

import lombok.RequiredArgsConstructor;
import org.iclass.story.dto.StoryDto;
import org.iclass.story.dto.StoryWithCommentsDto;
import org.iclass.story.repository.StoryRepository;
import org.iclass.story.type.SearchType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class StoryService {

    private final StoryRepository storyRepository;
    @Transactional(readOnly = true)
    public Page<StoryDto> searhStories(SearchType searchType, String searchKeyword, Pageable pageable) {
        return Page.empty();
    }

    @Transactional(readOnly = true)
    public StoryWithCommentsDto getStory(long storyId){

        return null;
    }


    public void saveStory(StoryDto dto){

    }

    public void updateStory(StoryDto dto){

    }

    public void deleteStory(long storyId){

    }

}
