package com.example.project.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/loginProc")
    fun home() : String{
        return "Home"
    }
}