package hello.postBoard2.web.post;

import hello.postBoard2.domain.SessionConst;
import hello.postBoard2.domain.member.Member;
import hello.postBoard2.domain.member.MemberRepository;
import hello.postBoard2.domain.post.Post;
import hello.postBoard2.domain.post.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/post")
@Controller
public class PostController {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

//    @GetMapping("/your-endpoint")
//    public ResponseEntity<?> yourMethod(HttpServletRequest request) {
//        String authHeader = request.getHeader("Authorization");
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String token = authHeader.substring(7); // "Bearer " 문자열 제거
//            // 이제 token을 검증하고 처리할 수 있습니다.
//        }
//        // 기타 처리
//    }



    @GetMapping("/post-form")
    public String postForm(
            @SessionAttribute(name = SessionConst.LOG_KEY, required = false) Member loginMember,
            Model model,
            HttpServletRequest request)
    {
//        if(loginMember == null){
//            log.info("post-form 접근 불가 : login 정보 없음");
//            return "redirect:/";
//        }
        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String jwtToken = authHeader.substring(7);
            log.info("PostController : jwtToken = {}", jwtToken);
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
            Model model,
            HttpServletRequest request){
//        if(loginMember == null){
//            log.info("postList-form 접근 불가 : login 정보 없음");
//            return "redirect:/";
//        }

        String authHeader = request.getHeader("Authorization");
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            String jwtToken = authHeader.substring(7);
            log.info("PostController : jwtToken = {}", jwtToken);
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
