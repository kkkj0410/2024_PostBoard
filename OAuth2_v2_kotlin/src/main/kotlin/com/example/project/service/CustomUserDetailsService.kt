package com.example.project.service

import com.example.project.converter.ProviderUserConverter
import com.example.project.converter.ProviderUserRequest
import com.example.project.entity.Role
import com.example.project.entity.User
import com.example.project.model.Principal
import com.example.project.model.ProviderUser
import com.example.project.model.dto.UserDto
import com.example.project.repository.UserRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    val userRepository : UserRepository,
    val providerUserConverter : ProviderUserConverter<ProviderUserRequest, ProviderUser>
) : UserDetailsService {
    private val logger = KotlinLogging.logger {}

    override fun loadUserByUsername(username: String?): UserDetails? {
        var principal : Principal?
        var user = userRepository.findByName(username)
        val userDto = userToUserDto(user)
        val providerUserRequest = ProviderUserRequest(null, null, userDto)
        val providerUser = providerUserConverter.converter(providerUserRequest)

        if(providerUser == null){
            logger.info("providerUser is null")
            return null
        }

        principal = Principal(providerUser)
        logger.info("principal : {}", principal)
        return principal
    }

    private fun userToUserDto(user : User?) : UserDto{
        var userDto : UserDto?
        if(user == null){
            userDto = UserDto(
                id = 0,
                loginId = "1",
                password = "{noop}password",
                name = "3",
                email = "4",
                role = Role.USER)
        }
        else {
            userDto = UserDto(
                id = user.id,
                loginId = user.loginId,
                password = user.password,
                name = user.name,
                email = user.email,
                role = user.role
            )
        }

        return userDto
    }


}