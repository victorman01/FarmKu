package com.ubaya.testdeploy

import androidx.lifecycle.ViewModel
import okhttp3.MultipartBody

class PreprocessViewModel(private val rep: Repository) : ViewModel() {
    val preprocess = rep.preprocess
    fun result(file: MultipartBody.Part) = rep.preprocessRepository(file)
}