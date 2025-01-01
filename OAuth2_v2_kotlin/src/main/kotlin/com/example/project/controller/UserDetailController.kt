package com.example.project.controller

import com.example.project.service.CustomUserDetailsService
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
class UserDetailController(val userService : CustomUserDetailsService) {
//    private val logger = KotlinLogging.logger {}
//
//    @PostMapping("/register")
//    fun register(@RequestParam username: String,
//                 @RequestParam password: String,
//                 @RequestParam email: String): String {
//
//        logger.info("username : {}, password : {}, email : {}", username, password, email)

//        return "redirect:/"
//    }

    @GetMapping("/success")
    fun success() : String {
        return "login success"
    }
}