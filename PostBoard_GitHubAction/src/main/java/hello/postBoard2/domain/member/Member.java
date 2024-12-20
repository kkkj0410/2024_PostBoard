package hello.postBoard2.domain.member;

import hello.postBoard2.domain.post.Post;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Entity
public class Member {

    @Id
    @GeneratedValue
    @JoinColumn(name = "member_id")
    private Long id;

    //@NotEmpty
    private String loginId;
    //@NotEmpty
    private String name;

    @OneToMany(mappedBy = "member")
    private List<Post> posts;

    public Member() {
    }

    public Member(String loginId, String name) {
        this.loginId = loginId;
        this.name = name;
    }

    private void setId(Long id) {
        this.id = id;
    }

    private void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    private void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(id, member.getId()) && Objects.equals(loginId, member.getLoginId()) && Objects.equals(name, member.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, loginId, name);
    }
}
