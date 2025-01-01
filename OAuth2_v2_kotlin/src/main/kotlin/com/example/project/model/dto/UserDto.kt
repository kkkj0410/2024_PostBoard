package com.example.project.model.dto

import com.example.project.entity.Role

data class UserDto(
    var id : Long?,
    var loginId : String?,
    var password : String?,
    var name : String?,
    var email : String?,
    var role : Role?,
)
