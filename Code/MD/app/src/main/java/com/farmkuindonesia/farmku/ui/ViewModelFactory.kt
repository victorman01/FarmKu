package com.farmkuindonesia.farmku.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.farmkuindonesia.farmku.ui.splashscreen.SplashScreenViewModel

class ViewModelFactory() : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SplashScreenViewModel::class.java) -> {
                SplashScreenViewModel() as T
            }

//            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
//                RegisterViewModel(rep) as T
//            }
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
//        @Volatile
//        private var instance: ViewModelFactory? = null
//        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
//            instance ?: ViewModelFactory(Injection.provideRepository(context))
//        }
    }
}