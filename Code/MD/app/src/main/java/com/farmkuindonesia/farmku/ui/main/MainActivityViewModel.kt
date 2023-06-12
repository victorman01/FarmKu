package com.farmkuindonesia.farmku.ui.main

import androidx.lifecycle.ViewModel
import com.farmkuindonesia.farmku.database.Repository
import com.farmkuindonesia.farmku.database.model.User

class MainActivityViewModel(private val rep: Repository) : ViewModel()  {
    val message = rep.messages
    val isLoading = rep.isLoading
//    fun logout() = rep.logOutUser()
}