package com.ubaya.testdeploy

import retrofit2.Call
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import com.ubaya.testdeploy.PreprocessResponse

interface ApiService {
    @Multipart
    @POST("preprocess")
    fun addImage(
        @Part image:MultipartBody.Part
    ): Call<PreprocessResponse>
}