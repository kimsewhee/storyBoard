package org.iclass.story.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString(callSuper = true)     //부모클래스 필드 포함해서 메소드 정의됩니다.
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class StoryComment extends AuditingFields{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter @ManyToOne(optional = false)  private Story story;
    @Setter @ManyToOne(optional = false)  private UserAccount userAccount;
    @Setter @Column(nullable = false,length = 500)
    private String content;

    protected StoryComment(){}
    private StoryComment(Story story, UserAccount userAccount,String content) {
        this.story = story;
        this.userAccount=userAccount;
        this.content = content;
    }

    public static StoryComment of(Story story,UserAccount userAccount,String content){
        return new StoryComment(story,userAccount,content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoryComment that = (StoryComment) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
