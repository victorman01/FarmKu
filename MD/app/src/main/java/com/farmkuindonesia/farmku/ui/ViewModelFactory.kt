package com.farmkuindonesia.farmku.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.farmkuindonesia.farmku.database.Repository
import com.farmkuindonesia.farmku.ui.fragment.home.HomeFragmentViewModel
import com.farmkuindonesia.farmku.ui.fragment.home.deteksipenyakit.DiseaseDetectionViewModel
import com.farmkuindonesia.farmku.ui.fragment.listland.ListLandViewModel
import com.farmkuindonesia.farmku.ui.fragment.listland.addland.AddLandViewModel
import com.farmkuindonesia.farmku.ui.fragment.listland.addmeasurement.AddMeasurementViewModel
import com.farmkuindonesia.farmku.ui.fragment.listland.land.LandViewModel
import com.farmkuindonesia.farmku.ui.fragment.listland.measurement.MeasurementViewModel
import com.farmkuindonesia.farmku.ui.fragment.profile.ProfileViewModel
import com.farmkuindonesia.farmku.ui.login.LoginViewModel
import com.farmkuindonesia.farmku.ui.main.MainActivityViewModel
import com.farmkuindonesia.farmku.ui.register.RegisterViewModel
import com.farmkuindonesia.farmku.ui.soildatacollection.SoilDataCollectionViewModel
import com.farmkuindonesia.farmku.ui.soildatacollection.add.AddSoilDataViewModel
import com.farmkuindonesia.farmku.utils.injection.Injection

class ViewModelFactory(private val rep: Repository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(rep) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(rep) as T
            }

            modelClass.isAssignableFrom(MainActivityViewModel::class.java) -> {
                MainActivityViewModel(rep) as T
            }

            modelClass.isAssignableFrom(HomeFragmentViewModel::class.java) -> {
                HomeFragmentViewModel(rep) as T
            }

            modelClass.isAssignableFrom(ListLandViewModel::class.java) -> {
                ListLandViewModel(rep) as T
            }

            modelClass.isAssignableFrom(DiseaseDetectionViewModel::class.java) -> {
                DiseaseDetectionViewModel(rep) as T
            }

            modelClass.isAssignableFrom(SoilDataCollectionViewModel::class.java) -> {
                SoilDataCollectionViewModel(rep) as T
            }

            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(rep) as T
            }

            modelClass.isAssignableFrom(LandViewModel::class.java) -> {
                LandViewModel(rep) as T
            }
            modelClass.isAssignableFrom(AddLandViewModel::class.java) -> {
                AddLandViewModel(rep) as T
            }
            modelClass.isAssignableFrom(AddSoilDataViewModel::class.java) -> {
                AddSoilDataViewModel(rep) as T
            }
            modelClass.isAssignableFrom(AddMeasurementViewModel::class.java) -> {
                AddMeasurementViewModel(rep) as T
            }
            modelClass.isAssignableFrom(MeasurementViewModel::class.java) -> {
                MeasurementViewModel(rep) as T
            }

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