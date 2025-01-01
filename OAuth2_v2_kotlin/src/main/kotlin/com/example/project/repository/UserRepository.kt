package com.example.project.repository

import com.example.project.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


interface UserRepository : JpaRepository<User, Long> {

    fun findByName(name : String?) : User?
}