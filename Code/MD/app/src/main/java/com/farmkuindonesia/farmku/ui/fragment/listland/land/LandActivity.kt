package com.farmkuindonesia.farmku.ui.fragment.listland.land

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.farmkuindonesia.farmku.databinding.ActivityLandBinding
import com.farmkuindonesia.farmku.ui.ViewModelFactory

class LandActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandBinding
    private lateinit var landActivityViewModel: LandViewModel
    private lateinit var viewModelFac: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModelFac = ViewModelFactory.getInstance(this)
        landActivityViewModel =
            ViewModelProvider(this, viewModelFac)[LandViewModel::class.java]

        val nameLand = intent.getStringExtra(NAMELAND)
        binding.txtLandName.text = nameLand
    }

    companion object {
        const val IDLAND = "IDLAND"
        const val NAMELAND = "NAMELAND"
    }
}