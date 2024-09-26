package hello.postBoard2.domain.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import hello.postBoard2.domain.member.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.Date;

@Slf4j
public class Jwt {
    private Member member;

    public Jwt(Member member){
        this.member = member;
    }

    public String makeJwt(){
        String jwtToken = JWT.create()
                .withSubject("Member토큰")
                .withExpiresAt(new Date(System.currentTimeMillis() + (60000*30)))
                .withClaim("id", this.member.getLoginId())
                .withClaim("name", this.member.getName())
                .sign(Algorithm.HMAC512("Jwt"));

        log.info("Jwt 토큰 생성 : {}", jwtToken);

        return jwtToken;
    }

//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//
//        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
//        System.out.println("JWT 토큰 생성 시작");
//        //Hash 암호 방식
//        String jwtToken = JWT.create()
//                .withSubject("cos토큰")
//                //만료 시간(System.currentTimeMillis() = 현재 시간)
//                .withExpiresAt(new Date(System.currentTimeMillis() + (60000*30)))
//
//                //JWT - Payload에 넣는 값
//                .withClaim("id", principalDetails.getUser().getId())
//                .withClaim("username", principalDetails.getUser().getUsername())
//                .sign(Algorithm.HMAC512("cos"));
//
//        System.out.println("JWT 토큰 보내기");
//        response.addHeader("Authorization", "Bearer " + jwtToken);
//    }
}
