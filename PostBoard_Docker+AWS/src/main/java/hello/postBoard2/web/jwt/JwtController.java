package hello.postBoard2.web.jwt;


import hello.postBoard2.domain.jwt.LoginResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class JwtController {

    @GetMapping("/auth/access")
    public ResponseEntity<LoginResponse> login(@Valid LoginResponse loginResponse){

        log.info(loginResponse.getAccessToken());

        ResponseEntity.ok(loginResponse);

        return ResponseEntity.ok(loginResponse);
    }

//    @GetMapping("/auth/access")
//    public void login(@Valid LoginResponse loginResponse
//                    , Model model){
//        log.info(loginResponse.getAccessToken());
//
//        model.addAttribute("token", loginResponse);
//    }

}
