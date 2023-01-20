package org.iclass.story.repository;

import org.iclass.story.domain.Story;
import org.iclass.story.domain.StoryComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryCommentRepository extends JpaRepository<StoryComment,Long> {

}
