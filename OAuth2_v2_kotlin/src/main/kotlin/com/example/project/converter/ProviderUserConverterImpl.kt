package com.example.project.converter

import com.example.project.model.ProviderUser
import org.springframework.stereotype.Component

@Component
class ProviderUserConverterImpl : ProviderUserConverter<ProviderUserRequest, ProviderUser> {
    private val converterList : List<ProviderUserConverter<ProviderUserRequest, ProviderUser>> = listOf(
        UserDetailsConverter(),
        OAuth2GoogleProviderUserConverter()
    )


    override fun converter(providerUserRequest : ProviderUserRequest) : ProviderUser?{
        var providerUser : ProviderUser?
        providerUser = null
        for(converter in converterList){
            providerUser = converter.converter(providerUserRequest)

            if(providerUser != null){
                break
            }
        }

        return providerUser
    }

}