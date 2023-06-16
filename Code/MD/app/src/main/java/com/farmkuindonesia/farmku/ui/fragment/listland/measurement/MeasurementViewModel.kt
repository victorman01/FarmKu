package com.farmkuindonesia.farmku.ui.fragment.listland.measurement

import androidx.lifecycle.ViewModel
import com.farmkuindonesia.farmku.database.Repository

class MeasurementViewModel(private val rep: Repository) : ViewModel() {
    val message = rep.messages
    val record = rep.record
    fun getRecord(measurementId: String) = rep.getRecord(measurementId)
}