package hello.postBoard2.web;


import hello.postBoard2.domain.post.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

////@GetMapping("/")
//public String homeLoginV3(HttpServletRequest request, Model model){
//    HttpSession session = request.getSession(false);
//
//    if(session == null)
//    {
//        return "home";
//    }
//
//    Member loginMember = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
//
//    //회원 데이터 X면
//    if(loginMember == null){
//        return "home";
//    }
//
//    //회원 데이터 있으면
//    model.addAttribute("member", loginMember);
//    return "loginHome";
//}

@Slf4j
@Controller
public class MainController {

    @Autowired
    PostRepository postRepository;

    @GetMapping
    public String mainForm(Model model)
    {
        model.addAttribute("posts", postRepository.findAll());

        return "main-form";
    }
}
