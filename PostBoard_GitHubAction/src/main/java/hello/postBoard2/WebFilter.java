package hello.postBoard2;


import hello.postBoard2.domain.filter.LoginFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


//        registry.addInterceptor(new LoginCheckInterceptor())
//        .order(2)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/members/add", "/login", "/logout", "/css/**", "/*.ico", "/error");

@Slf4j
@Configuration
public class WebFilter implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry){
        log.info("WebFilter 시작 : ");
        registry.addInterceptor(new LoginFilter())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/members/new-form", "/members/login","/css/**", "/*.ico", "/error");
    }


}
