package com.farmkuindonesia.farmku.ui.fragment.home

import androidx.lifecycle.ViewModel
import com.farmkuindonesia.farmku.database.Repository

class HomeFragmentViewModel(private val rep: Repository) : ViewModel() {
    val messages = rep.messages
    val isLoading = rep.isLoading
    val landCount = rep.listLand
    fun getUser() = rep.getUser()
    fun getCountLand(id: String?) = rep.getListLandByIdUser(id)
}