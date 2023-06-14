package com.farmkuindonesia.farmku.ui.soildatacollection.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.farmkuindonesia.farmku.databinding.ActivitySoilDataCollectionBinding
import com.farmkuindonesia.farmku.databinding.ActivitySoilDataCollectionDetailBinding
import com.farmkuindonesia.farmku.ui.soildatacollection.detail.maps.SoilDataCollectionDetailMapsActivity

class SoilDataCollectionDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySoilDataCollectionDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoilDataCollectionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Soil Data Detail"

        val latitude = intent.getDoubleExtra(LATITUDE, 0.0)
        val longitude = intent.getDoubleExtra(LONGITUDE, 0.0)

        binding.apply {
            txtNSoilDataDetail.text = "N = ${intent.getDoubleExtra(N, 0.0)}"
            txtPSoilDataDetail.text = "P = ${intent.getDoubleExtra(P, 0.0)}"
            txtKSoilDataDetail.text = "K = ${intent.getDoubleExtra(K, 0.0)}"
            txtPHSoilDataDetail.text = "pH = ${intent.getDoubleExtra(PH, 0.0)}"
            txtDescriptionSoilDataDetail.text = intent.getStringExtra(DESCRIPTION)
            btnShowLocation.setOnClickListener{
                val intent = Intent(this@SoilDataCollectionDetailActivity, SoilDataCollectionDetailMapsActivity::class.java)
                intent.putExtra(LATITUDE, latitude)
                intent.putExtra(LONGITUDE, longitude)
                startActivity(intent)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val ID = "ID"
        const val N = "N"
        const val P = "P"
        const val K = "K"
        const val PH = "PH"
        const val LONGITUDE = "LONGITUDE"
        const val LATITUDE = "LATITUDE"
        const val IMAGE = "IMAGE"
        const val DESCRIPTION = "DESCRIPTION"
    }
}