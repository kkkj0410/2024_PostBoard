package hello.postBoard2.domain.jwt;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;


@Getter
@Entity
public class Token {

    @Id
    @GeneratedValue
    @JoinColumn(name = "token_id")
    private long id;
    private String accessToken;
    private String refreshToken;

    public Token(Long id, String accessToken, String refreshToken){
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public Token updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;

        return this;
    }

}
