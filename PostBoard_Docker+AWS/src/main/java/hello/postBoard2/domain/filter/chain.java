package hello.postBoard2.domain.filter;

import hello.postBoard2.domain.jwt.JwtFilter;
import hello.postBoard2.domain.jwt.JwtFilter2;
import hello.postBoard2.domain.member.MemberRepository;
import hello.postBoard2.domain.oauth.CustomAuthenticationSuccessHandler;
import hello.postBoard2.domain.oauth.oauthLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class chain {

    //private final oauthLoginService oauthloginService = new oauthLoginService();

    @Autowired
    private oauthLoginService oauthloginService;

    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;


    @Bean
    public AuthenticationManager authenticationManager (AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    //cors 설정
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowCredentials(true);
//        configuration.addAllowedOriginPattern("*"); // 허용할 출처 설정
//        configuration.addAllowedHeader("*"); // 허용할 헤더 설정
//        configuration.addAllowedMethod("*"); // 허용할 메서드 설정 (GET, POST 등)
//
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration); // 모든 요청에 대한 CORS 설정
//        return source;
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager, CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) throws Exception {
        // CSRF 보호 비활성화
        http
                //oauth2 로그인에서는 활성화되지 않음
            //.addFilter(new JwtFilter(authenticationManager))
            .addFilter(new JwtFilter2(authenticationManager))
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/post/post-form/**").authenticated()
                    .requestMatchers("/post/postList-form/**").authenticated()
                    //위 3가지 경로 빼고는 접근 허용
                    .anyRequest().permitAll()
            )

            .formLogin(form -> form
                    .loginPage("/members/login") // 사용자 정의 로그인 페이지
                    .loginProcessingUrl("/login") // /login 주소 POST 호출 시, 시큐리티가 낚아채서 대신 로그인
                    .defaultSuccessUrl("/")
            )

            // /oauth2/authorization/google로 URL 접속 시, 아래 함수가 실행됨
            .oauth2Login(oauth2 -> oauth2
                    //로그인 시, 이동하는 창
                    //하지만 구글 로그인 창으로 이동되므로, 무시되는 명령임
                    .loginPage("/loginForm")
                    //oauthLoginService
                    .userInfoEndpoint(u -> u.userService(oauthloginService))
                        .successHandler(customAuthenticationSuccessHandler)
            ); // 구글 로그인이 완료된 뒤의 후 처리가 필요.


        return http.build(); // SecurityFilterChain 반환
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }




}
