package com.example.project.model.dto

import com.example.project.model.OAuth2ProviderUser
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.user.OAuth2User

class GoogleUserDto : OAuth2ProviderUser {

    constructor(
        oAuth2User: OAuth2User?,
        clientRegistration: ClientRegistration?,
        attributes: MutableMap<String, Any>?
    ) : super(oAuth2User, clientRegistration, attributes) { // super 호출
    }

    override fun getId(): String? {
        return attribute?.get("sub") as String?
    }

    override fun getUsername(): String? {
        return attribute?.get("name") as String?
    }

}