package hello.postBoard2.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class MemberRepositoryTest {


    @Autowired
    MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void save() throws Exception{
        Member member = new Member("a1","A1");

        memberRepository.saveMember(member);

        Member findMember = memberRepository.findMember(member.getId());

        Assertions.assertThat(member.getId()).isEqualTo(findMember.getId());
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());


    }

    @Test
    @Transactional
    @Rollback(false)
    public void findAll() throws Exception{
        Member member1 = new Member("a1", "A1");

        Member member2 = new Member("b1", "B1");

        memberRepository.saveMember(member1);
        memberRepository.saveMember(member2);
        List<Member> members = memberRepository.findAll();

        Assertions.assertThat(members.get(0).getId()).isEqualTo(member1.getId());
        Assertions.assertThat(members.get(1).getId()).isEqualTo(member2.getId());
    }

}