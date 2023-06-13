package com.farmkuindonesia.farmku.ui.fragment.home.deteksipenyakit

import androidx.lifecycle.ViewModel
import com.farmkuindonesia.farmku.database.Repository
import okhttp3.MultipartBody

class DiseaseDetectionViewModel(private val rep: Repository) : ViewModel() {
    val preprocess = rep.preprocess
    fun result(file: MultipartBody.Part) = rep.preprocessRepository(file)
}