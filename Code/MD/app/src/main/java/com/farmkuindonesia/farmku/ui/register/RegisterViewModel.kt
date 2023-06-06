package com.farmkuindonesia.farmku.ui.register

import androidx.lifecycle.ViewModel
import com.farmkuindonesia.farmku.database.Repository

class RegisterViewModel(private val rep: Repository) : ViewModel() {
    val messages = rep.messages
    val isLoading = rep.isLoading
    fun register(name: String, email: String, address: String, phone: String, password: String) =
        rep.signUp(name, email, address, phone, password)

    fun getProvince() = rep.getAllProvince()
}