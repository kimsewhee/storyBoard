package org.iclass.story.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString(callSuper = true)     //부모클래스 필드 포함해서 메소드 정의됩니다.
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Story extends AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     //MySql 의 auto_increment 이용하는 방식. 오라클도 잘됨.
    private Long id;
    /*
        @Entity
        @SequenceGenerator(   //오라클의 시퀀스 이용하는 방식
          name = "MEMBER_SEQ_GENERATOR", 
          sequenceName = "MEMBER_SEQ", // 매핑할 데이터베이스 시퀀스 이름 
          initialValue = 1,
          allocationSize = 1)       //기본값 50 : next call 로 시퀀스 가져올때 50개를 미리가져온다. 시퀀스 가져오기 위한 성능저하 방지
        public class Member {
          @Id
          @GeneratedValue(strategy = GenerationType.SEQUENCE,
                          generator = "MEMBER_SEQ_GENERATOR")
          private Long id; 
        }
        https://gmlwjd9405.github.io/2019/08/12/primary-key-mapping.html
    */

    @Setter @ManyToOne(optional = false)
    private UserAccount userAccount;
    /*
    엔티티를 조회할 때 연관된 엔티티도 함께 조회한다.
    즉시 로딩(EAGER LOADING)을 사용하려면 @ManyToOne의 fetch 속성을 FetchType.EAGER로 지정한다.
    두 테이블을 조인해서 쿼리 한 번으로 두 엔티티를 모두 조회한다.
    조인 컬럼 외래 키가 NULL 값을 허용한다면 내부 조인했을 때 조회할 수 없는 데이터가 생긴다.
    따라서 JPA 는 이런 상황을 고려해서 외부 조인을 사용한다.
    하지만 일반적으로 외부 조인보다 내부 조인이 성능과 최적화에서 더 유리하다.
    따라서 내부 조인을 사용하려면 외래 키에 NOT NULL 제약 조건을 설정하면 값이 항상 있음을 보장할 수 있어 JPA가 내부 조인만 사용하게 한다.
    * nullable 설정에 따른 조인 전략
    @JoinColumn(nullable = true) : NULL 허용(기본값), 외부 조인 사용
    @JoinColumn(nullable = false) : NULL 허용 X, 내부 조인 사용
    또는 @ManyToOne.optional = false 로 설정해도 내부 조인을 사용한다.
    ---
    연관된 엔티티를 실제 사용하는 시점에 JPA가 SQL을 호출해서 조회한다.
    지연 로딩(LAZY LOADING)을 사용하려면 @ManyToOne의 fetch속성을 FetchType.Lazy 로 지정한다.
    @Entity
    public class Member {
        //...
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "TEAM_ID")
        private Team team;
        //...
    }

    지연 로딩 실행 코드
    Member member = em.find(Member.class, "member1");
    Team team = member.getTeam();   //프록시 객체
    team.getName();                 //실제 사용 순간

    지연 로딩 SQL
    // em.find(Member.class, "member1");
    SELECT * FROM MEMBER
    WHERE MEMBER_ID = 'member1'
    // team.getName() 호출
    SELECT * FROM TEAM
    WHERE TEAM_ID = 'team1'
    ---
    ??     조회 대상이 영속성 컨텍스트에 이미 있으면 프록시 객체를 사용할 이유가 없다.    => 실제 객체 사용
     */
    @Setter @Column(nullable = false)
    private String title;
    @Setter @Column(nullable = false,length = 10000) @Lob       //오라클은 4000바이트 이상은 CLOB 타입
    private String content;

    @Setter
    private String hashtag;

    @OrderBy("createdAt DESC")
    @OneToMany(mappedBy = "story",cascade=CascadeType.ALL)
    @ToString.Exclude
    private final Set<StoryComment> storyComments = new LinkedHashSet<>();

    protected Story(){}

    private Story(UserAccount userAccount, String title, String content, String hashtag) {
        this.userAccount=userAccount;
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }
    private Story( String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Story of(UserAccount userAccount,String title, String content,String hashtag) {
        return new Story(userAccount,title,content,hashtag);
    }

    public static Story of(String title, String content,String hashtag) {
        return new Story(title,content,hashtag);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Story story = (Story) o;
        return id.equals(story.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
