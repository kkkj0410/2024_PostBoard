package com.example.project.converter

import com.example.project.entity.User
import com.example.project.model.dto.UserDto
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.user.OAuth2User

data class ProviderUserRequest(
    val clientRegistration : ClientRegistration?,
    val oAuth2User : OAuth2User?,
    val userDto : UserDto?
)
