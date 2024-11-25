package hello.postBoard2.web.member;

import hello.postBoard2.domain.SessionConst;
import hello.postBoard2.domain.member.Member;
import hello.postBoard2.domain.member.MemberRepository;
import hello.postBoard2.domain.post.PostRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
//URL에 기본으로 추가됨
@RequestMapping("/members")
public class MemberController {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PostRepository postRepository;

    @GetMapping("/new-form")
    public String newForm(Model model)
    {
        model.addAttribute("member", new Member());
        return "members/new-form";
    }

//    @PostMapping("/new-form")
//    public String newForm(@Valid @ModelAttribute Member member)
//    {
//        memberRepository.saveMember(member);
//        return "redirect:/";
//    }

    @PostMapping("/new-form")
    public String newForm(@RequestParam String loginId, @RequestParam String name)
    {
        Member member = new Member(loginId,name);
        memberRepository.saveMember(member);
        return "redirect:/";
    }


    @GetMapping("/login")
    public String login(Model model)
    {
        log.info("로그인 창 접속");
        model.addAttribute("member", new Member());
        return "members/login";
    }

//    @PostMapping("/login")
//    public String login(@Valid @ModelAttribute Member member,
//                        HttpServletRequest request,
//                        @RequestParam(defaultValue = "/") String redirectURL)
//    {
//        Member findMember = memberRepository.Login(member.getLoginId(), member.getName());
//        if(findMember == null)
//        {
//            log.info("회원 가입 실패 : {}", member);
//            log.info("회원 가입 실패 이름 : {}", member.getName());
//            return "redirect:/members/new-form";
//        }
//
//        HttpSession session = request.getSession(true);
//
//        session.setAttribute(SessionConst.LOG_KEY, findMember);
//
//        log.info("회원 가입 성공 : {}", findMember);
//        return "redirect:" + redirectURL;
//    }

    @PostMapping("/login")
    public String login(@RequestParam String loginId, @RequestParam String name,
                        HttpServletRequest request,
                        @RequestParam(defaultValue = "/") String redirectURL)
    {
        Member findMember = memberRepository.Login(loginId, name);
        if(findMember == null)
        {
            log.info("회원 가입 실패 : {}", loginId);
            log.info("회원 가입 실패 이름 : {}", name);
            return "redirect:/members/new-form";
        }

        HttpSession session = request.getSession(true);

        session.setAttribute(SessionConst.LOG_KEY, findMember);

        log.info("회원 가입 성공 : {}", findMember);
        return "redirect:" + redirectURL;
    }

}
