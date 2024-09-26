package hello.postBoard2.domain.jwt.service;

import hello.postBoard2.domain.jwt.Token;
import hello.postBoard2.domain.jwt.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//생성자 또는 스프링 빈을 끌고옴. 따라서 @Autowired 대신에 사용 가능
@RequiredArgsConstructor
@Service
public class TokenService {

    //final로 선언 시, Autowired 사용 불가
    //@Autowired는 해당 객체가 생성된 이후에 스프링 빈을 끌고옴
    //따라서, final로 이미 만들어진 후이므로 스프링 빈으로 바꿀 수가 없음
    //@Autowired
    private final TokenRepository tokenRepository;

    public void save(Long id, String accessToken, String refreshToken){
        Token token = tokenRepository.findByAccessToken(accessToken)
                .map(o -> o.updateRefreshToken(refreshToken))
                .orElseGet(() -> new Token(id,accessToken,refreshToken));


        tokenRepository.save(token);
    }
}
