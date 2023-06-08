package com.ubaya.testdeploy

import android.content.Context
import java.util.prefs.Preferences

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService)
    }
}