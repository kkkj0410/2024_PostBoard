package com.example.project.model

import org.springframework.security.core.GrantedAuthority

interface ProviderUser {

    fun getId() : String?
    fun getLoginId() : String?
    fun getUsername() : String?
    fun getPassword() : String?
    fun getEmail() : String?
    fun getProvider() : String?
    fun getAttributes() : MutableMap<String, Any>?
    fun getAuthorities() : MutableCollection<out GrantedAuthority>?

}