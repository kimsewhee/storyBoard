package org.iclass.story.repository;

import org.iclass.story.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface StoryRepository extends JpaRepository<Story,Long> {

}
