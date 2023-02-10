package org.iclass.story;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Disabled("spring data rest 테스트 후 비활성화")
@DisplayName("Data REST - API 테스트")
@Transactional
@AutoConfigureMockMvc
@SpringBootTest
public class DataRestTest {
    private final MockMvc mvc;

    public DataRestTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("/api 게시글 리스트")
    @Test
    void requestStoryListTest() throws Exception {
        mvc.perform(get("/api/stories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
                .andDo(print());
    }

    @DisplayName("/api 게시글 PK 조회")
    @Test
    void requestStoryTest() throws Exception {
        mvc.perform(get("/api/stories/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")))
                .andDo(print());
    }

    @DisplayName("/api 게시글의 댓글 조회")
    @Test
    void requestStoryCommentTest() throws Exception {
        mvc.perform(get("/api/stories/1/storyComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName("/api 댓글리스트 조회")
    @Test
    void requestStoryCommentListTest() throws Exception {
        mvc.perform(get("/api/storyComments"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }

    @DisplayName("/api 댓글 PK 조회")
    @Test
    void requestStoryCommentOneTest() throws Exception {
        mvc.perform(get("/api/storyComments/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("application/hal+json")));
    }
// application 실행하고 브라우저에서 http://localhost:8081/api/ 요청하면 hal explorer 실행된다.

    @DisplayName("/api 회원 관련은 제공하지 않는다.")
    @Test
    void requestUserAccountTest() throws Exception {
        mvc.perform(get("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(post("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(put("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(patch("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(delete("/api/userAccounts")).andExpect(status().isNotFound());
        mvc.perform(head("/api/userAccounts")).andExpect(status().isNotFound());

    }
}
