package hello.postBoard2.domain.jwt.repository;

import hello.postBoard2.domain.jwt.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TokenRepository extends CrudRepository<Token,String> {

    //여기에 적는 함수는 특수한 규칙을 가짐(해당 규칙에 따라 함수를 적으면 해당 함수 기능을 제공)
    //find(찾는 기능) + By(대상은?) + AccessToken(엑세스 토큰)
    Optional<Token> findByAccessToken(String accessToken);
}
