package com.ubaya.testdeploy

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Multipart
import java.util.prefs.Preferences
import kotlin.math.log

class Repository constructor(private val apiService: ApiService) {
    private val _preprocess = MutableLiveData<PreprocessResponse>()
    val preprocess: LiveData<PreprocessResponse> = _preprocess

    fun preprocessRepository(file: MultipartBody.Part){
        Log.d("image", file.toString())
        val client = apiService.addImage(file)
        Log.d("clientssss", client.toString())
        client.enqueue(object : Callback<PreprocessResponse> {
            override fun onResponse(
                call: Call<PreprocessResponse>,
                response: Response<PreprocessResponse>  
            ) {
                if (response.isSuccessful) {
                    Log.d("hasil", response.body().toString())
                    _preprocess.value = response.body()
                } else {
                    Log.d("Eror", "eror disini")
                }
            }

            override fun onFailure(call: Call<PreprocessResponse>, t: Throwable) {
                Log.d("Eror2", t.message.toString())
            }
        })
    }

    companion object {
        private const val TAG = "Repository"
        private var instance: Repository? = null

        fun getInstance(apiService: ApiService): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService)
            }.also { instance = it }
    }
}