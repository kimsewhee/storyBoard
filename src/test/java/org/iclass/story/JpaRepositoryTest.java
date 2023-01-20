package org.iclass.story;

import org.iclass.story.config.JpaConfig;
import org.iclass.story.domain.Story;
import org.iclass.story.repository.StoryCommentRepository;
import org.iclass.story.repository.StoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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

}
