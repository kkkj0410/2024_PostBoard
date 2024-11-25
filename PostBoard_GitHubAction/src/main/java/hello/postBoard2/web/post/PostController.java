package hello.postBoard2.web.post;

import hello.postBoard2.domain.SessionConst;
import hello.postBoard2.domain.member.Member;
import hello.postBoard2.domain.member.MemberRepository;
import hello.postBoard2.domain.post.Post;
import hello.postBoard2.domain.post.PostRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
public class PostController {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/post-form")
    public String postForm(
            @SessionAttribute(name = SessionConst.LOG_KEY, required = false) Member loginMember,
            Model model)
    {
        if(loginMember == null){
            return "redirect:/";
        }

        Post post = new Post();
        post.setMember(loginMember);

        model.addAttribute("post", post);

        return "post/post-form";
    }

    @PostMapping("/post-form")
    public String postForm(@Valid @ModelAttribute Post post,
                           @SessionAttribute(name = SessionConst.LOG_KEY, required = false) Member loginMember)
    {
        if(loginMember == null){
            return "redirect:/";
        }

        post.setMember(loginMember);
        postRepository.savePost(post);

        log.info("게시글 저장 성공 = {}", post);
        return "redirect:/";
    }

    @GetMapping("/postList-form")
    public String postListForm(
            @SessionAttribute(name = SessionConst.LOG_KEY, required = false) Member loginMember,
            Model model){
        if(loginMember == null){
            return "redirect:/";
        }

        List<Post> postList = postRepository.findPostByMember(loginMember);
        model.addAttribute("postList", postList);

        return "post/postList-form";
    }

    @GetMapping("edit-form/{postId}")
    public String editForm(@PathVariable long postId, Model model){
        Post findPost = postRepository.findPostById(postId);
        if(findPost == null){
            return "redirect:/";
        }

        model.addAttribute("post", findPost);
        return "post/edit-form";
    }

    @PostMapping("edit-form/{postId}")
    public String editForm(@PathVariable long postId,
                           @Valid @ModelAttribute Post post){
        postRepository.editPost(postId, post);

        return "redirect:/postList-form";
    }

    @PostMapping("edit-form/delete/{postId}")
    public String delete(@PathVariable long postId){
        postRepository.deleteById(postId);

        return "redirect:/postList-form";
    }

}
