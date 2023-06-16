package com.farmkuindonesia.farmku.ui.soildatacollection

import androidx.lifecycle.ViewModel
import com.farmkuindonesia.farmku.database.Repository

class SoilDataCollectionViewModel(private val rep: Repository) : ViewModel() {
    val messages = rep.messages
    val isLoading = rep.isLoading
    val soilData = rep.soilData
    fun getSoilData() = rep.getSoilData()
}