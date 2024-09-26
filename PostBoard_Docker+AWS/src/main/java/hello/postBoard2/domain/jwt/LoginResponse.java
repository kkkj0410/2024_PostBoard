package hello.postBoard2.domain.jwt;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginResponse {
    private String accessToken;

    public LoginResponse(String accessToken){
        this.accessToken = accessToken;
    }
}
