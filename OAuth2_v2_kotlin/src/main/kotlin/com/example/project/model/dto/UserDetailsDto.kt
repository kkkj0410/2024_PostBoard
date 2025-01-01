package com.example.project.model.dto

import com.example.project.model.ProviderUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

class UserDetailsDto(
    val userDto : UserDto
) : ProviderUser {
    override fun getId(): String? {
        return userDto.id as String?
    }

    override fun getLoginId(): String? {
        return userDto.loginId
    }

    override fun getUsername(): String? {
        return userDto.name
    }

    override fun getPassword(): String? {
        return userDto.password
    }

    override fun getEmail(): String? {
        return userDto.email
    }

    override fun getProvider(): String {
        return "none"
    }

    override fun getAttributes(): MutableMap<String, Any>? {
        return null
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return mutableListOf(SimpleGrantedAuthority("ROLE_USER"))
    }

}