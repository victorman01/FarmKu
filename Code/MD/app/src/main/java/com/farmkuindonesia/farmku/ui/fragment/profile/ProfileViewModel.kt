package com.farmkuindonesia.farmku.ui.fragment.profile

import androidx.lifecycle.ViewModel
import com.farmkuindonesia.farmku.database.Repository

class ProfileViewModel(private val rep: Repository) : ViewModel() {
    fun getUser() = rep.getUser()
}