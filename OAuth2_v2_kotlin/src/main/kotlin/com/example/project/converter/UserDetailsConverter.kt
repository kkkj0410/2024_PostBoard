package com.example.project.converter

import com.example.project.model.ProviderUser
import com.example.project.model.dto.GoogleUserDto
import com.example.project.model.dto.UserDetailsDto
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsConverter : ProviderUserConverter<ProviderUserRequest, ProviderUser>  {
    override fun converter(providerUserRequest: ProviderUserRequest): ProviderUser? {
        if(providerUserRequest.userDto != null){
            val userDetailsDto = UserDetailsDto(providerUserRequest.userDto)
            return userDetailsDto
        }

        return null
    }
}