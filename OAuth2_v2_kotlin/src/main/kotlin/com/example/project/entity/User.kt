package com.example.project.entity

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long?,
    var loginId : String?,
    var password : String?,
    var name : String?,
    var email : String?,
    @Enumerated(EnumType.STRING)
    var role : Role?,
) {
}