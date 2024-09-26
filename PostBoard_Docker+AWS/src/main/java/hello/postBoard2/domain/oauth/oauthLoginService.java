package hello.postBoard2.domain.oauth;

import hello.postBoard2.domain.jwt.Jwt;
import hello.postBoard2.domain.member.Member;
import hello.postBoard2.domain.member.MemberRepository;
import jakarta.security.auth.message.AuthException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;


@Slf4j
@Service
public class oauthLoginService extends DefaultOAuth2UserService {

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
    @Autowired
    private MemberRepository memberRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("======oauthLoginService===========");

        //로그인 유저 전체 정보
        Map<String, Object> oauth2UserAttributes = super.loadUser(userRequest).getAttributes();
        log.info("oauth2UserAttributes : {}", oauth2UserAttributes);

        //회사
        String provider = userRequest.getClientRegistration().getRegistrationId();
        log.info("provider : {}", provider);

        //유저 정보 담기
        Oauth2UserInfo oauth2UserInfo = Oauth2UserInfo.company(provider, oauth2UserAttributes);
        Member member = saveMember(oauth2UserInfo);


        log.info("return : {}", PrincipalDetails.builder()
                .member(member)
                .oauth2UserAttributes(oauth2UserAttributes)
                .build());

        return PrincipalDetails.builder()
                .member(member)
                .oauth2UserAttributes(oauth2UserAttributes)
                .build();
    }

    private Member saveMember(Oauth2UserInfo oauth2UserInfo){
        Member member = oauth2UserInfo.entity();
        log.info("Member : {}", member.toString());
        log.info("memberRepository : {}", memberRepository);

        if(memberRepository.Login(member.getLoginId(), member.getName()) == null){
            log.info("최초 등록 멤버 : {}", member);

            memberRepository.saveMember(member);
        }
        else{
            log.info("이미 등록된 멤버 : {}", member);
        }

        return member;
    }
}
