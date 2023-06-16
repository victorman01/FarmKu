package com.farmkuindonesia.farmku.ui.fragment.listland.land

import androidx.lifecycle.ViewModel
import com.farmkuindonesia.farmku.database.Repository

class LandViewModel(private val rep:Repository):ViewModel() {
    val messages = rep.messages
    val weatherData = rep.weatherData
    val measurementData = rep.measurement
    fun getWeatherData(lat: Double?, lon: Double?) = rep.getWeatherData(lat,lon)
    fun getMeasurement(idLand:String) = rep.getMeasurementList(idLand)
    fun getUserId()=rep.getUser()
}