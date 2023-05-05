package org.iclass.story.service;

import org.iclass.story.domain.Story;
import org.iclass.story.dto.StoryCommentDto;
import org.iclass.story.repository.StoryCommentRepository;
import org.iclass.story.repository.StoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직-댓글")
@ExtendWith(MockitoExtension.class)
class StoryCommentServiceTest {

    @InjectMocks private StoryCommentService sut;
    @Mock private StoryRepository storyRepository;
    @Mock private StoryCommentRepository storyCommentRepository;

    @DisplayName("게시글 ID로 조회하면, 해당 댓글목록을 반환한다.")
    @Test
    public void givenStoryId_whenSearch_thenReturnStoryComments() {
        //given
        Long stroyId = 1L;
        given(storyRepository.findById(stroyId)).willReturn(Optional.of(Story.of("제목", "내용", "#해시태그")));

        //when
        List<StoryCommentDto> storyComments = sut.searchStroyComments(stroyId);

        //then
        assertThat(storyComments).isNotNull();
        then(storyRepository).should().findById(stroyId);
    }
/*
    @DisplayName("댓글 입력값으로 댓글을 저장한다.")
    @Test
    public void given_when_then() {
        //given
        given(storyCommentRepository.save(any(StoryComment.class)))
                .willReturn(null);
        //when
        sut.saveStoryComment(StoryCommentDto.of("korsec",LocalDateTime.now(),"korsec",LocalDateTime.now(),"korsec","댓글"));

        //then
        then(storyCommentRepository).should().save(any(StoryComment.class));
    } */
}
