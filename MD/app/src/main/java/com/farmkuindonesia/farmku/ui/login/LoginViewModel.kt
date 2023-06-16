package com.farmkuindonesia.farmku.ui.login

import androidx.lifecycle.ViewModel
import com.farmkuindonesia.farmku.database.Repository
import com.farmkuindonesia.farmku.database.model.User

class LoginViewModel(private val rep: Repository) : ViewModel() {
    val messages = rep.messages
    val isLoading = rep.isLoading
    val userLoginData = rep.userLogin
    fun login(email: String, pass: String) = rep.signIn(email, pass)
}