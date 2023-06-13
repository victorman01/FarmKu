package com.farmkuindonesia.farmku.utils.injection

import android.content.Context
import com.farmkuindonesia.farmku.database.Preferences
import com.farmkuindonesia.farmku.database.Repository
import com.farmkuindonesia.farmku.database.config.ApiConfig

object Injection {
    fun provideRepository(context: Context): Repository {
        val preferences = Preferences.getInstance(context)
        val apiService = ApiConfig.getApiServiceML()
        return Repository.getInstance(preferences, apiService)
    }
}