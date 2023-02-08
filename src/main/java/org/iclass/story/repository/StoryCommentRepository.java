package org.iclass.story.repository;

import org.iclass.story.domain.Story;
import org.iclass.story.domain.StoryComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface StoryCommentRepository extends JpaRepository<StoryComment,Long> {

}
