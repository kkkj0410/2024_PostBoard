package hello.postBoard2.domain.post;

import hello.postBoard2.domain.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
public class Post {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    //@NotEmpty
    private String title;
    //@NotEmpty
    private String content;

    public Post() {
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    //    public Post(Member member, String title, String content) {
//        this.member = member;
//        this.title = title;
//        this.content = content;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(member, post.getMember()) && Objects.equals(title, post.getTitle()) && Objects.equals(content, post.getContent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, title, content);
    }
}
