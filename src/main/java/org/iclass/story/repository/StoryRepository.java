package org.iclass.story.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.iclass.story.domain.QStory;
import org.iclass.story.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface StoryRepository extends JpaRepository<Story,Long> , QuerydslPredicateExecutor<Story>, QuerydslBinderCustomizer<QStory> {

    @Override
    default void customize(QuerydslBindings bindings, QStory root){
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title,root.content,root.hashtag,root.createdAt,root.createdBy);
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
