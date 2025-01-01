package com.example.project.service

import com.example.project.entity.Role
import com.example.project.entity.User
import com.example.project.model.ProviderUser
import com.example.project.repository.UserRepository
import org.springframework.stereotype.Service

@Service
abstract class AbstractUserService(
    val userRepository : UserRepository
) {

    fun save(providerUser : ProviderUser?) : Boolean{
        if(providerUser == null){
            return false
        }

        val user = providerUserToUser(providerUser)
        userRepository.save(user)

        return true
    }

    private fun providerUserToUser(providerUser : ProviderUser) : User {
        val user = User(
                        id = null,
                        loginId = providerUser.getLoginId(),
                        password = providerUser.getPassword(),
                        name = providerUser.getUsername(),
                        email = providerUser.getEmail(),
                        role = Role.USER)

        return user
    }

}
