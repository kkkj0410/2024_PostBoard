package com.example.project.service

import com.example.project.converter.ProviderUserConverter
import com.example.project.converter.ProviderUserRequest
import com.example.project.model.Principal
import com.example.project.model.ProviderUser
import com.example.project.repository.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service


@Service
class CustomOAuth2UserService(
    override val userRepository : UserRepository,
    val providerUserConverter : ProviderUserConverter<ProviderUserRequest, ProviderUser>
) : AbstractUserService(userRepository) ,OAuth2UserService<OAuth2UserRequest, OAuth2User>{

    private val logger = KotlinLogging.logger {}

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User? {
        val clientRegistration = userRequest?.getClientRegistration()
        val defaultOAuth2UserService = DefaultOAuth2UserService()
        val oAuth2User = defaultOAuth2UserService.loadUser(userRequest)

        val providerUserRequest = ProviderUserRequest(clientRegistration, oAuth2User, null)
        logger.info("oAuth2User : {}", oAuth2User)

        val providerUser : ProviderUser? = providerUserConverter.converter(providerUserRequest)
        if(providerUser == null){
            logger.info("User is null")
            return null
        }

        super.save(providerUser)

        logger.info("principal : {}", Principal(providerUser))
        return Principal(providerUser)
    }

}