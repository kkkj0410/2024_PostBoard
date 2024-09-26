package hello.postBoard2.domain.post;

import hello.postBoard2.domain.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Valid;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@Transactional
public class PostRepository {

    private Map<Long,Post> postList = new HashMap<>();
    private Long sequence = 0L;

    @PersistenceContext
    private EntityManager em;

    public PostRepository() {
    }


    public void savePost(Post post){

        em.persist(post);

//        post.setId(sequence++);
//        postList.put(post.getId(),post);
    }

    public Post findPostById(Long id){
        return em.find(Post.class, id);
    }



    public void editPost(long postId, Post post){
        Post findPost = findPostById(postId);
        findPost.setTitle(post.getTitle());
        findPost.setContent(post.getContent());

        em.persist(findPost);
    }

    public void deleteById(Long id){
        Post post = findPostById(id);

        if(post == null){
            return;
        }

        em.remove(post);
    }

    public List<Post> findAll(){
        return em.createQuery("SELECT p From Post p",Post.class).getResultList();
    }


    public List<Post> findPostByMember(Member member){

        return findAll().stream()
                .filter(p -> p.getMember().equals(member))
                        .collect(Collectors.toList());
    }

}
