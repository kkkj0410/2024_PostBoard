package com.example.project.converter

import com.example.project.model.ProviderUser
import com.example.project.model.dto.GoogleUserDto

class OAuth2GoogleProviderUserConverter : ProviderUserConverter<ProviderUserRequest, ProviderUser> {

    override fun converter(providerUserRequest: ProviderUserRequest): ProviderUser? {
        val clientName = providerUserRequest.clientRegistration?.clientName

        if(clientName.equals("Google")){
            val oAuth2User = providerUserRequest.oAuth2User
            val clientRegistration = providerUserRequest.clientRegistration
            val attributes = oAuth2User?.getAttributes()

            val googleUser = GoogleUserDto(oAuth2User, clientRegistration, attributes)
            return googleUser
        }

        return null
    }

}