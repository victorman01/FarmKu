package com.farmkuindonesia.farmku.database.model

data class User(
    val id: String?,
    val name: String?,
    val email: String?,
    val phoneNumber: String?,
    val address: Address?,
    val role: String?,
    val createdAt: String?,
    val updatedAt: String?
)

data class Address(
    val id: String?,
    val province: String?,
    val district: String?,
    val regency: String?,
    val village: String?
)

