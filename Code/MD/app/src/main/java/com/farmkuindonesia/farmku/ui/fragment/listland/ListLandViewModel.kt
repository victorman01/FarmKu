package com.farmkuindonesia.farmku.ui.fragment.listland

import androidx.lifecycle.ViewModel
import com.farmkuindonesia.farmku.database.Repository

class ListLandViewModel(private val rep: Repository) : ViewModel() {
    val messages = rep.messages
    val isLoading = rep.isLoading
    val listLand = rep.listLand
    fun getListLand(id: String) = rep.getListLandByIdUser(id)
    fun getUserId() = rep.getUser()
}