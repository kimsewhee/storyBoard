package org.iclass.story.repository;

import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.iclass.story.domain.QStory;
import org.iclass.story.domain.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface StoryRepository extends JpaRepository<Story,Long> ,
        QuerydslPredicateExecutor<Story>,       //기본 검색기능 추가(테스트는 아래 코드 모두 없이 대소문자 구분, 부분 검색 되는지 확인하기)
        QuerydslBinderCustomizer<QStory>        //부분 검색기능 추가. 메소드 오버라이딩 필요
{

    Page<Story> findByTitle(String title, Pageable pageable);
    @Override
    default void customize(QuerydslBindings bindings, QStory root){
        bindings.excludeUnlistedProperties(true);       //리스팅되지 않은 속성은 제외시키기
        bindings.including(root.title,root.content,root.hashtag,root.createdAt,root.createdBy);
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
