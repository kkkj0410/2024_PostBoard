package hello.postBoard2.domain.post;

import hello.postBoard2.domain.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    @Transactional
    @Rollback(false)
    public void findAll(){
        Post post1 = new Post("title1", "content1");
        Member member1 = new Member("Id11","name11");
        post1.setMember(member1);

        Post post2 = new Post("title2", "content2");
        Member member2 = new Member("Id11","name11");
        post2.setMember(member2);

        em.persist(member1);
        em.persist(member2);

        postRepository.savePost(post1);
        postRepository.savePost(post2);
        List<Post> postList = postRepository.findAll();

        Assertions.assertThat(postList.get(0).getTitle()).isEqualTo(post1.getTitle());
        Assertions.assertThat(postList.get(1).getTitle()).isEqualTo(post2.getTitle());

    }

    @Test
    @Transactional
    @Rollback(false)
    public void savePost(){
        Post post = new Post("title1", "content1");
        Member member1 = new Member("Id11","name11");
        post.setMember(member1);

        em.persist(member1);

        postRepository.savePost(post);
        Post findPost = postRepository.findPostById(post.getId());

        Assertions.assertThat(findPost.getTitle()).isEqualTo(post.getTitle());

    }


    @Test
    @Transactional
    @Rollback(false)
    public void findPostByMember(){
        Post post1 = new Post("title1", "content1");
        Member member1 = new Member("Id11","name11");
        post1.setMember(member1);

        Post post2 = new Post("title2", "content2");
        Member member2 = new Member("Id11","name11");
        post2.setMember(member2);

        em.persist(member1);
        em.persist(member2);

        postRepository.savePost(post1);
        postRepository.savePost(post2);
        List<Post> postList = postRepository.findPostByMember(member1);

        Assertions.assertThat(postList.get(0).getId()).isEqualTo(member1.getId());

    }


    @Test
    @Transactional
    @Rollback(false)
    public void editPost(){
        Post post = new Post("title1", "content1");
        Member member = new Member("Id11","name11");
        post.setMember(member);

        em.persist(member);

        postRepository.savePost(post);

        Post editPost = new Post("reviseTitle", "reviseContent");
        postRepository.editPost(post.getId(), editPost);

        Post findPost = postRepository.findPostById(post.getId());

        Assertions.assertThat(findPost.getTitle()).isEqualTo(editPost.getTitle());

    }
}