package com.farmkuindonesia.farmku.ui.soildatacollection.add

import androidx.lifecycle.ViewModel
import com.farmkuindonesia.farmku.database.Repository
import okhttp3.MultipartBody

class AddSoilDataViewModel(private val rep: Repository) : ViewModel() {
    val message = rep.pesan
    val isLoading = rep.isLoading
    fun send(name: String, n: Double, p: Double, k: Double, ph: Double, lon: Double, lat: Double, file: MultipartBody.Part, desc: String) = rep.addSoilData(name, n, p, k, ph, lon, lat, file, desc)
}