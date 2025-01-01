package com.example.project.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

class Principal(val providerUser : ProviderUser) : UserDetails, OAuth2User {

    override fun getName(): String? {
        return providerUser.getUsername()
    }

    override fun getAttributes(): MutableMap<String, Any>? {
        return providerUser.getAttributes()
    }


    override fun getPassword(): String? {
        return providerUser.getPassword()
    }

    override fun getUsername(): String? {
        return providerUser.getUsername()
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return providerUser.getAuthorities()
    }

    override fun toString(): String {
        return "Principal(providerUser=$providerUser)"
    }


}