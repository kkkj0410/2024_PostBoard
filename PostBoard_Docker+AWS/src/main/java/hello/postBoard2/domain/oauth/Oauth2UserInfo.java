package hello.postBoard2.domain.oauth;

import hello.postBoard2.domain.member.Member;
import hello.postBoard2.domain.member.Role;
import jakarta.security.auth.message.AuthException;
import lombok.Builder;

import java.util.Map;

@Builder
public record Oauth2UserInfo(String loginId, String name, String email, String provider) {

    public static Oauth2UserInfo company(String provider, Map<String, Object> oauth2UserAttributes){
        return switch(provider){
            case "google" -> companyGoogle(provider,oauth2UserAttributes);
            default -> null;
        };
    }

    private static Oauth2UserInfo companyGoogle(String provider, Map<String, Object> oauth2UserAttributes){
        return Oauth2UserInfo.builder()
                .loginId((String)oauth2UserAttributes.get("sub"))
                .name((String)oauth2UserAttributes.get("name"))
                .email((String)oauth2UserAttributes.get("email"))
                .provider(provider)
                .build();
    }

    public Member entity(){
        return new Member(loginId, name, email, provider, Role.ROLE_USER);
    }

}
