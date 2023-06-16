package com.farmkuindonesia.farmku.ui.fragment.listland.measurement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.databinding.ActivityAddLandBinding
import com.farmkuindonesia.farmku.databinding.ActivityMeasurementBinding

class MeasurementActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMeasurementBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeasurementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}