package org.iclass.story.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.iclass.story.domain.QStoryComment;
import org.iclass.story.domain.StoryComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface StoryCommentRepository
        extends JpaRepository<StoryComment,Long> ,
        QuerydslPredicateExecutor<StoryComment>,
        QuerydslBinderCustomizer<QStoryComment> {

    List<StoryComment> findByStory_Id(Long storyId);        //댓글의 Id 가 아닌 Story 의 Id

    @Override
    default void customize(QuerydslBindings bindings, QStoryComment root){
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.content,root.createdAt,root.createdBy);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
