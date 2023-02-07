package org.iclass.story;

import org.iclass.story.config.JpaConfig;
import org.iclass.story.domain.Story;
import org.iclass.story.repository.StoryCommentRepository;
import org.iclass.story.repository.StoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {

    private final StoryRepository storyRepository;
    private final StoryCommentRepository storyCommentRepository;

    public JpaRepositoryTest(
            @Autowired  StoryRepository storyRepository,
            @Autowired  StoryCommentRepository storyCommentRepository) {
        this.storyRepository = storyRepository;
        this.storyCommentRepository = storyCommentRepository;
    }

    @DisplayName("insert 테스트")
    @Test
    void whenInsert() {
        long previousCOunt = storyRepository.count();

        Story story = storyRepository.save(Story.of("today is..", "하하하hahaha", "#today"));
        assertThat(storyRepository.count()).isEqualTo(previousCOunt+1);
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // Given

        // When
        List<Story> stories = storyRepository.findAll();

        // Then
        assertThat(stories)
                .isNotNull()
                .hasSize(123);
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        // Given
        Story article = storyRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        // When
        Story savedArticle = storyRepository.saveAndFlush(article);

        // Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        // Given
        Story article = storyRepository.findById(1L).orElseThrow();
        long previousArticleCount = storyRepository.count();
        long previousArticleCommentCount = storyRepository.count();
        int deletedCommentsSize = article.getStoryComments().size();

        // When
        storyRepository.delete(article);

        // Then
        assertThat(storyRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(storyCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);
    }

}
