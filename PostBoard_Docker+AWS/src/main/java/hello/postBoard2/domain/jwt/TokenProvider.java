package hello.postBoard2.domain.jwt;

import hello.postBoard2.domain.jwt.service.TokenService;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {
    @Value("${jwt.key}")
    private String key;
    private SecretKey secretKey;
    private final long ACCESS_EXPIRE_TIME = 1000 * 60 * 30L;
    private final long REFRESH_EXPIRE_TIME = 1000 * 60 * 60L * 24 * 7;
    private String KEY_ROLE = "role";

    @Autowired
    TokenService tokenService;

    // @PostConstruct : 스프링 빈 등록 후, 자동 실행
    @PostConstruct
    //JWT에 서명할 키를 암호화
    private void setSecretKey() {
        log.info("key : {}", key);

        secretKey = Keys.hmacShaKeyFor(key.getBytes());
    }

    public String makeAccessToken(Authentication authentication){
        String token = makeToken(authentication, ACCESS_EXPIRE_TIME);
        log.info("token : {}", token);

        return token;
    }

    public String makeRefreshToken(Authentication authentication){
        String token = makeToken(authentication, REFRESH_EXPIRE_TIME);
        log.info("token : {}", token);

        return token;
    }

    public String makeToken(Authentication authentication, long expired){
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expired);

        log.info("authentication : {}", authentication);

        log.info("authentication.getAuthorities() : {}", authentication.getAuthorities());

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining());

        log.info("authorities : {}", authorities);
        return Jwts.builder()
                .subject(authentication.getName())
                .claim(KEY_ROLE, authorities)
                .issuedAt(now)
                .expiration(expiredDate)
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();

    }



}
