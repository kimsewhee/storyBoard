package org.iclass.story.controller;

import org.iclass.story.config.TestSecurityConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@DisplayName("view 컨트롤러-게시글")
@Import(TestSecurityConfig.class)
@WebMvcTest(StoryController.class)          //StoryController.class 만 테스트
public class StoryControllerTest {
    private final MockMvc mvc;

    public StoryControllerTest(@Autowired MockMvc mvc) {   //Test 클래스에서 Autowired는 생략불가
        this.mvc = mvc;
    }

    @DisplayName("view 게시글 리스트-전체")
    @Test
    void storyControllerTest() throws Exception {   //get import 는 ctrl+스페이스바 후 alt+enter
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
        mvc.perform(get("/stories/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("stories/detail"))
                .andExpect(model().attributeExists("story"))
                .andExpect(model().attributeExists("storyComments"));
    }
}
