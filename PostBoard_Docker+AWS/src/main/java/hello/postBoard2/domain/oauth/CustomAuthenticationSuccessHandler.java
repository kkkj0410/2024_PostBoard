package hello.postBoard2.domain.oauth;

import hello.postBoard2.domain.jwt.Jwt;
import hello.postBoard2.domain.jwt.TokenProvider;
import hello.postBoard2.domain.member.Member;
import hello.postBoard2.domain.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    //해당 객체 사용 시, chain에 있는 스프링 빈을 찾기 때문에 사이클 발생
    //chain <-> CustomAuthenticationSuccessHandler,
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenProvider tokenProvider;

    private String uri = "/auth/access";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("==============onAuthenticationSuccess==============");
        log.info("authentication : {}", authentication);

        String accessToken = tokenProvider.makeAccessToken(authentication);

        String redirectUrl = UriComponentsBuilder.fromUriString(uri)
                .queryParam("accessToken", accessToken)
                .build().toUriString();

        response.sendRedirect(redirectUrl);


//
//        log.info("Authentication : {}", authentication);
//        OAuth2User oauth2User = (OAuth2User)authentication.getPrincipal();
//
//        log.info("oauth2User : {}", oauth2User);
//        //log.info("name : {}", oauth2User.getUsername());
//
//        // OAuth2AuthenticationToken으로 캐스팅하여 OAuth2 제공자 정보 가져오기
//        if (authentication instanceof OAuth2AuthenticationToken) {
//            OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
//
//            String provider = oauthToken.getAuthorizedClientRegistrationId();
//            String providerId = oauth2User.getAttribute("sub");
//
//            String loginId = bCryptPasswordEncoder.encode("whatever");
//            log.info("{}", loginId);
//            String name = provider + "_" + providerId; // google_123123123
//            String email = oauth2User.getAttribute("email");
//            String role = "ROLE_USER";
//
//            Member member = new Member(loginId, name, email, role, provider, providerId);
//            if(memberRepository.Login(member.getLoginId(), member.getName()) == null){
//                log.info("최초 등록 멤버 : {}", member);
//
//                memberRepository.saveMember(member);
//            }
//            else{
//                log.info("이미 등록된 멤버 : {}", member);
//            }
//
//            Jwt jwt = new Jwt(member);
//            String jwtString = jwt.makeJwt();
//
//            //Authorization : 사용자->서버로 보내는 인증 정보
//            //Bearer : 토큰 종류(Oauth2 종류의 인증 토큰)
//            //Bearer 다음에 ' '공백을 추가해야 토큰과 Bearer 사이의 구분이 가능해짐
//            response.setHeader("Authorization", "Bearer " + jwtString);

//
//        log.info("==============onAuthenticationSuccess==============");
//        response.sendRedirect("/");

    }

}
