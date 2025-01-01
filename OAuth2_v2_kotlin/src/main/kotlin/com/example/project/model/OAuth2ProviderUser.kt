package com.example.project.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.user.OAuth2User
import java.util.*
import java.util.stream.Collectors

abstract class OAuth2ProviderUser(
    val oAuth2User: OAuth2User?,
    val clientRegistration: ClientRegistration?,
    val attribute: MutableMap<String, Any>?
) : ProviderUser{

    override fun getLoginId() : String?{
        return null
    }
    override fun getPassword() : String? {
        return UUID.randomUUID().toString();
    }

    override fun getEmail(): String? {
        return attribute?.get("email") as String?
    }

    override fun getProvider() : String? {
        return clientRegistration?.getRegistrationId()
    }

    override fun getAttributes() : MutableMap<String, Any>? {
        return attribute
    }

    override fun getAuthorities() : MutableCollection<out GrantedAuthority>? {
        val authorities: MutableCollection<GrantedAuthority>? = oAuth2User?.authorities?.map { authority ->
            SimpleGrantedAuthority(authority.authority)
        }?.toMutableList()
        return authorities
        }
    }
