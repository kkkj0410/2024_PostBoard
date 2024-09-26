package hello.postBoard2.domain.member;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Slf4j
@Repository
@Transactional
public class MemberRepository {

    private Map<Long, Member> members = new HashMap<>();
    private Long sequence = 0L;

    //private static final MemberRepository instance = new MemberRepository();

    @PersistenceContext
    private EntityManager em;

//    public static MemberRepository getInstance() {
//        return instance;
//    }

    public MemberRepository() {
    }


    public void saveMember(Member member)
    {
        if(Login(member.getLoginId(), member.getName()) != null){
            log.info("회원 저장 실패(동일 회원 존재) id: {}", member.getId());
            return;
        }
        log.info("회원 저장 성공 id: {}", member.getId());

        em.persist(member);

    }

    public Member findMember(Long id){
        return em.find(Member.class, id);
    }

    public Member Login(String loginId, String name){
        return findLoginId(loginId)
                .filter(m->m.getName().equals(name))
                .orElse(null);
    }

    public Optional<Member> findLoginId(String loginId){
        return findAll().stream()
                .filter(m -> m.getLoginId().equals(loginId))
                .findFirst();
    }

    @Transactional
    public List<Member> findAll()
    {
        return em.createQuery("SELECT m From Member m", Member.class).getResultList();
        //return new ArrayList<>(members.values());
    }

}
