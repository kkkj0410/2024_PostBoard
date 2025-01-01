package com.example.project.config

import com.example.project.entity.Role
import com.example.project.service.CustomOAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig(val customOAuth2UserService : CustomOAuth2UserService) {

    @Bean
    fun securityFilterChain(http : HttpSecurity) : SecurityFilterChain {
        http
            .authorizeHttpRequests { auth -> auth
                .requestMatchers("/login/**").permitAll()
                .requestMatchers("/success").hasRole("USER")
                .anyRequest().authenticated()
            }
            .formLogin{principal -> principal
                .loginPage("/loginProc")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/success", false)
                .permitAll()
            }

            .oauth2Login { oauth2 -> oauth2
                .defaultSuccessUrl("/", false)
//                .failureUrl("/login")
                .permitAll()
                .userInfoEndpoint{ userInfoEndpointConfig -> userInfoEndpointConfig
                    .userService(customOAuth2UserService)
                }
            }

        return http.build()
    }
}