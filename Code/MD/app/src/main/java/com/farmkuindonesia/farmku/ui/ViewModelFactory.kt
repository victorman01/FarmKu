package com.farmkuindonesia.farmku.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.farmkuindonesia.farmku.database.Repository
import com.farmkuindonesia.farmku.ui.login.LoginViewModel
import com.farmkuindonesia.farmku.utils.injection.Injection

class ViewModelFactory(private val rep: Repository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(rep) as T
            }
//
//            modelClass.isAssignableFrom(ListStoryViewModel::class.java) -> {
//                ListStoryViewModel(rep) as T
//            }
//
//            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
//                AddStoryViewModel(rep) as T
//            }

            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(Injection.provideRepository(context))
        }
    }
}