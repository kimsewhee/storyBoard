package org.iclass.story.service;

import org.iclass.story.domain.Story;
import org.iclass.story.dto.StoryDto;
import org.iclass.story.dto.StoryUpdateDto;
import org.iclass.story.repository.StoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("BizLogic - 게시글")
@ExtendWith(MockitoExtension.class)
class StoryServiceTest {        //mockito 테스트
    @InjectMocks private StoryService sut;      //system under test
    @Mock private StoryRepository storyRepository;

    /* 1. 검색   2. 각 게시글 페이지로 이동 3. 페이지네이션  4. 홈버튼   5. 정렬 */

    @DisplayName("게시글을 검색하면, 게시글 리스트를 반환한다.")
    @Test
    void givenSearchParameter_whenSearch_thenReturnsStoryList() {
        Object SearchType;
        Page<StoryDto> stories = sut.searhStories(org.iclass.story.type.SearchType.TITLE, "search keyword");
        assertThat(stories).isNotNull();
    }

    @DisplayName("게시글을 id로 조회한다.")
    @Test
    public void givenId_whenSearching_thenReturnStory() {
        //given

        //when
        StoryDto dto = sut.searchStory(1L);
        //then
        assertThat(dto).isNotNull();
    }
    
    
    @DisplayName("입력한 게시글 정보를 저장한다.")
    @Test
    public void givenValue_whenSave_thenSaveStory() {
        //given
        given(storyRepository.save(any(Story.class)))
                .willReturn(null);
        //when
        sut.saveStory(StoryDto.of("제목","내용","#해시태그", LocalDateTime.now(),"korsec"));
        //then
        then(storyRepository).should().save(any(Story.class));
    }

    @DisplayName("id 게시글을 입력된 정보로 수정한다.")
    @Test
    public void givenIdandValue_whenUpdate_thenUpdateStory() {
        //given
        given(storyRepository.save(any(Story.class)))
                .willReturn(null);
        //when
        sut.updateStory(1L, StoryUpdateDto.of("제목", "내용", "#해시태그"));
        //then
        then(storyRepository).should().save(any(Story.class));
    }

    @DisplayName("id 게시글을 삭제한다.")
    @Test
    public void givenId_whenDelete_thenDeletesStory() {
        //given
        willDoNothing().given(storyRepository).delete(any(Story.class));

        //when
        sut.deleteStory(1L);

        //then
        then(storyRepository).should().delete(any(Story.class));
    }
}
/*
*   @ExtendWith(MockitoExtension.class): 테스트 클래스가 Mockito를 사용함을 의미합니다.
    @Mock: 실제 구현된 객체 대신에 Mock 객체를 사용하게 될 클래스를 의미합니다. 테스트 런타임 시 해당 객체 대신 Mock 객체가 주입되어 Unit Test가 처리됩니다.
    @InjectMocks: Mock 객체가 주입된 클래스를 사용하게 될 클래스를 의미합니다.
    * 테스트 런타임 시 클래스 내부에 선언된 멤버 변수들 중에서 @Mock으로 등록된 클래스의 변수에 실제 객체 대신 Mock 객체가 주입되어 Unit Test가 처리됩니다.
* *** solitary vs sociable 테스트 ??*/
