package com.farmkuindonesia.farmku.ui.soildatacollection.add

import androidx.lifecycle.ViewModel
import com.farmkuindonesia.farmku.database.Repository
import com.farmkuindonesia.farmku.database.responses.ImageItem
import com.farmkuindonesia.farmku.database.responses.SoilDataCollectionResponseItem
import okhttp3.MultipartBody

class AddSoilDataViewModel(private val rep: Repository) : ViewModel() {
    val message = rep.messages
    val isLoading = rep.isLoading
    fun send(namaVarietas: String, n: Double, p: Double, k: Double, pH: Double, longitude: Double, latitude: Double, image: MultipartBody.Part, description: String) = rep.addSoilData(namaVarietas, n, p, k, pH, longitude, latitude, image, description)
}