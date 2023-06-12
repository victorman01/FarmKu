package com.farmkuindonesia.farmku.ui.fragment.home

import androidx.lifecycle.ViewModel
import com.farmkuindonesia.farmku.database.Repository

class HomeFragmentViewModel(private val rep: Repository) : ViewModel() {
    val messages = rep.messages
    val isLoading = rep.isLoading

    fun getUser() = rep.getUser()
}