package org.iclass.story.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.iclass.story.domain.Story;
import org.iclass.story.dto.StoryDto;
import org.iclass.story.dto.StoryWithCommentsDto;
import org.iclass.story.repository.StoryRepository;
import org.iclass.story.type.SearchType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class StoryService {

    private final StoryRepository storyRepository;

    @Transactional(readOnly = true)
    public Page<StoryDto> searhStories(SearchType searchType, String searchKeyword, Pageable pageable) {
        if(searchKeyword==null || searchKeyword.isBlank()){
            return storyRepository.findAll(pageable).map(StoryDto::from);
        }

        return switch (searchType){
            case TITLE -> storyRepository.findByTitleContaining(searchKeyword,pageable).map(StoryDto::from);
            case CONTENT -> storyRepository.findByContentContaining(searchKeyword,pageable).map(StoryDto::from);
            case ID -> storyRepository.findByUserAccount_UserIdContaining(searchKeyword,pageable).map(StoryDto::from);
            case NICKNAME -> storyRepository.findByUserAccount_NicknameContaining(searchKeyword,pageable).map(StoryDto::from);
            case HASHTAG -> storyRepository.findByHashtag("#"+searchKeyword,pageable).map(StoryDto::from);
        };
    }

   // @Transactional(readOnly = true)
    public StoryWithCommentsDto getStory(Long storyId){
        return storyRepository.findById(storyId)
                .map(StoryWithCommentsDto::from)
                .orElseThrow(()-> new EntityNotFoundException("게시글이 없습니다.- storyId: " + storyId));
    }


    public void saveStory(StoryDto dto){
        storyRepository.save(dto.toEntity());
    }

    public void updateStory(StoryDto dto){
        try{
            Story story = storyRepository.getReferenceById(dto.id());
            if(dto.title() != null) story.setTitle(dto.title());
            if(dto.content() !=null) story.setContent(dto.content());
            story.setHashtag(dto.hashtag());
        }catch (EntityNotFoundException e){
            log.warn("게시글 업데이트 실패 - 게시글을 찾을 수 없습니다. - {}",dto);
        }
    }

    public void deleteStory(long storyId){
        storyRepository.deleteById(storyId);
    }

}
