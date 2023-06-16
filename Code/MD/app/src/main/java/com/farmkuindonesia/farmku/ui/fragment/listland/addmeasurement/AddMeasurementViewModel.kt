package com.farmkuindonesia.farmku.ui.fragment.listland.addmeasurement

import androidx.lifecycle.ViewModel
import com.farmkuindonesia.farmku.database.Repository

class AddMeasurementViewModel(private val rep:Repository) : ViewModel(){
    val messages = rep.messages
    fun addMeasurement(landId:String, userId:String) = rep.addMeasurement(landId,userId)
    fun getUserId() = rep.getUser()
}