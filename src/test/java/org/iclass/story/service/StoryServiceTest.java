package org.iclass.story.service;

import org.iclass.story.domain.Story;
import org.iclass.story.domain.UserAccount;
import org.iclass.story.dto.StoryDto;
import org.iclass.story.dto.StoryWithCommentsDto;
import org.iclass.story.dto.UserrAccountDto;
import org.iclass.story.repository.StoryRepository;
import org.iclass.story.type.SearchType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@DisplayName("BizLogic - 게시글")
@ExtendWith(MockitoExtension.class)
class StoryServiceTest {        //mockito 테스트
    @InjectMocks private StoryService sut;      //system under test
    @Mock private StoryRepository storyRepository;

    /* 1. 검색   2. 각 게시글 페이지로 이동 3. 페이지네이션  4. 홈버튼   5. 정렬 */

    @DisplayName("검색어 없이 게시글을 검색하면, 게시글 페이지를 반환한다.")
    @Test
    void givenNoSearchParameter_whenSearch_thenReturnsStoryList() {
        Pageable pageable = Pageable.ofSize(20);
        given(storyRepository.findAll(pageable)).willReturn(Page.empty());

        //when
        Page<StoryDto> stories = sut.searhStories(null,null,pageable);

        //then
        assertThat(stories).isEmpty();
        then(storyRepository).should().findAll(pageable);
    }

    @DisplayName("검색어와 함께 게시글을 검색하면, 게시글 페이지를 반환하다.")
    @Test
    public void givenSearchParameter_whenSearching_thenReturnStoryPage() {
        //given
        SearchType searchType = SearchType.TITLE;
        String searchKeyword = "title";
        Pageable pageable = Pageable.ofSize(20);
        given(storyRepository.findByTitle(searchKeyword,pageable)).willReturn(Page.empty());
        //when
        Page<StoryDto> stories = sut.searhStories(searchType,searchKeyword,pageable);
        //then
        assertThat(stories).isEmpty();
        then(storyRepository).should().findByTitle(searchKeyword,pageable);
    }


    @DisplayName("게시글을 조회하면 게시글을 반환한다.")
    @Test
    public void givenId_whenSearching_thenReturnStory() {
        //given
        Long storyId = 1L;
        Story story = createStory();
        given(storyRepository.findById(storyId)).willReturn(Optional.of(story));
        //when
        StoryWithCommentsDto dto = sut.getStory(1L);
        //then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("title",story.getTitle())
                .hasFieldOrPropertyWithValue("content",story.getContent())
                .hasFieldOrPropertyWithValue("hashtag",story.getHashtag());
        then(storyRepository).should().findById(storyId);
    }

    @DisplayName("없는 게시글을 조회하면 예외를 던진다.")
    @Test
    public void givenNotExistStory_whenSearch_thenThrowException() {
        //given
        Long storyId = 0L;
        given(storyRepository.findById(storyId)).willReturn(Optional.empty());

        //when
        Throwable t = catchThrowable(() -> sut.getStory(storyId));

        //then
        assertThat(t)
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("조회한 게시글이 없습니다. - storyId:" + storyId);
        then(storyRepository).should().findById(storyId);
    }
    
    @DisplayName("게시글을 입력하면 게시글 정보를 생성한다.")
    @Test
    public void givenStory_whenSave_thenSaveStory() {
        //given
        StoryDto dto = createStoryDto();
        given(storyRepository.save(any(Story.class)))
                .willReturn(createStory());
        //when
        sut.saveStory(dto);
        //then
        then(storyRepository).should().save(any(Story.class));
    }

    @DisplayName("게시글의 수정 정보를 입력하면, 게시글을 수정한다.")
    @Test
    public void givenStoryInfoForModify_whenUpdate_thenUpdateStory() {
        //given
        given(storyRepository.save(any(Story.class)))
                .willReturn(null);
        Story story = createStory();
        StoryDto dto = createStoryDto("new title","new content","#spring");
        given(storyRepository.getReferenceById(dto.id())).willReturn(story);

        //when
        sut.updateStory(dto);
        //then
        assertThat(story)
                .hasFieldOrPropertyWithValue("title",dto.title())
                .hasFieldOrPropertyWithValue("content",dto.content())
                .hasFieldOrPropertyWithValue("hashtag",dto.hashtag());

        then(storyRepository).should().getReferenceById(dto.id());
    }

    @DisplayName("없는 게시글의 수정 정보를 입력하면, 경고 로그 출력하고 아무것도 하지 않는다.")
    @Test
    public void givenNotExistStory_whenUpdate_thenDoesNothing() {
        //given
        StoryDto storyDto = createStoryDto("새글","아무 내용","#spring");
        given(storyRepository.getReferenceById(storyDto.id())).willThrow(EntityNotFoundException.class);

        //when
        sut.updateStory(storyDto);

        //then
        then(storyRepository).should().getReferenceById(storyDto.id());
    }

    @DisplayName("게시글의 id를 지정하면, 게시글을 삭제한다.")
    @Test
    public void givenId_whenDelete_thenDeletesStory() {
        //given
        Long storyId =1L;
        willDoNothing().given(storyRepository).deleteById(storyId);

        //when
        sut.deleteStory(1L);

        //then
        then(storyRepository).should().deleteById(storyId);
    }

    //이하 fixture
    private UserAccount createUserAccount() {
        return UserAccount.of(
                "",
                "",
                "",
                "",
                null
        );
    }

    private Story createStory(){
        return Story.of(
                createUserAccount(),
                "title",
                "content",
                "#java"
        );
    }

    private UserrAccountDto createUserAccountDto(){
        return UserrAccountDto.of(
                1L,
                "",
                "",
                "",
                "",
                "",
                LocalDateTime.now(),
                "",
                LocalDateTime.now(),
                ""
        );
    }
    private StoryDto createStoryDto() {
        return createStoryDto("title","content","#java");
    }
    private StoryDto createStoryDto(String title,String content,String hashtag){
        return StoryDto.of(1L,
                createUserAccountDto(),
                title,
                content,
                hashtag,
                LocalDateTime.now(),
                "",
                LocalDateTime.now(),
                "");
    }
}
/*
*   @ExtendWith(MockitoExtension.class): 테스트 클래스가 Mockito를 사용함을 의미합니다.
    @Mock: 실제 구현된 객체 대신에 Mock 객체를 사용하게 될 클래스를 의미합니다. 테스트 런타임 시 해당 객체 대신 Mock 객체가 주입되어 Unit Test가 처리됩니다.
    @InjectMocks: Mock 객체가 주입된 클래스를 사용하게 될 클래스를 의미합니다.
    * 테스트 런타임 시 클래스 내부에 선언된 멤버 변수들 중에서 @Mock으로 등록된 클래스의 변수에 실제 객체 대신 Mock 객체가 주입되어 Unit Test가 처리됩니다.
* *** solitary vs sociable 테스트 ??*/
