package org.iclass.story.controller;

import org.iclass.story.config.TestSecurityConfig;
import org.iclass.story.dto.StoryWithCommentsDto;
import org.iclass.story.dto.UserrAccountDto;
import org.iclass.story.service.StoryService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DisplayName("view 컨트롤러-게시글")
@Import(TestSecurityConfig.class)
@WebMvcTest(StoryController.class)          //StoryController.class 만 테스트
public class StoryControllerTest {
    private final MockMvc mvc;

    @MockBean private StoryService storyService;

    public StoryControllerTest(@Autowired MockMvc mvc) {   //Test 클래스에서 Autowired는 생략불가
        this.mvc = mvc;
    }

    @DisplayName("view 게시글 리스트-전체")
    @Test
    void storyControllerTest() throws Exception {   //get import 는 ctrl+스페이스바 후 alt+enter
        given(storyService.searhStories(eq(null),eq(null),any(Pageable.class))).willReturn(Page.empty());

        mvc.perform(get("/stories"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))        //text/html;charset=UTF-8
                .andExpect(view().name("stories/index"))
                .andExpect(model().attributeExists("stories"));
    }

    @Disabled
    @DisplayName("view 게시글 리스트-검색")
    @Test
    void storyControllerSearchTest() throws Exception {
        mvc.perform(get("/stories/search"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("stories/search"));
    }

    @Disabled
    @DisplayName("view 게시글 리스트-해시태그 검색")
    @Test
    void storyControllerHashtagSearchTest() throws Exception {
        mvc.perform(get("/stories/search-hashtag"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(model().attributeExists("stories/search-hashtag"));
    }

    @DisplayName("view 게시글 - 상세보기")
    @Test
    void whenStoryViewRequesting_thenReturnStoryOne() throws Exception {
        Long storyId = 1L;
        given(storyService.getStory(storyId)).willReturn(createStoryWithCommentsDto());
        mvc.perform(get("/stories/" + storyId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("stories/detail"))
                .andExpect(model().attributeExists("story"))
                .andExpect(model().attributeExists("storyComments"));
        then(storyService).should().getStory(storyId);
    }

    private StoryWithCommentsDto createStoryWithCommentsDto() {

        return StoryWithCommentsDto.of(
                1L,
                createUserAccountDto(),
                Set.of(),
                "title",
                "content",
                "#java",
                LocalDateTime.now(),
                "koreait",
                LocalDateTime.now(),
                "koreait"
        );
    }

    private UserrAccountDto createUserAccountDto() {

        return UserrAccountDto.of(1L,
                "koreait",
                "pw",
                "koreait@gmail.com",
                "koreait",
                "memo",
                LocalDateTime.now(),
                "koreait",
                LocalDateTime.now(),
                "koreait");
    }
}
