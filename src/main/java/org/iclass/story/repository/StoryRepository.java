package org.iclass.story.repository;

import org.iclass.story.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story,Long> {

}
