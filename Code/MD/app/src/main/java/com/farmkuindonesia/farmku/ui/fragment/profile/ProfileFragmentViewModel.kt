package com.farmkuindonesia.farmku.ui.fragment.profile

import androidx.lifecycle.ViewModel
import com.farmkuindonesia.farmku.database.Repository

class ProfileFragmentViewModel(private val rep: Repository) : ViewModel() {

    fun logout() = rep.logOutUser()
}