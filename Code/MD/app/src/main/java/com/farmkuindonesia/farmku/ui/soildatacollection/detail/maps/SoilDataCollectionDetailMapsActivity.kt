package com.farmkuindonesia.farmku.ui.soildatacollection.detail.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.databinding.ActivitySoilDataCollectionDetailMapsBinding
import com.farmkuindonesia.farmku.ui.soildatacollection.detail.SoilDataCollectionDetailActivity

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class SoilDataCollectionDetailMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
private lateinit var binding: ActivitySoilDataCollectionDetailMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding = ActivitySoilDataCollectionDetailMapsBinding.inflate(layoutInflater)
         setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Soil Data Detail"

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val latitude = intent.getDoubleExtra(SoilDataCollectionDetailActivity.LATITUDE, 0.0)
        val longitude = intent.getDoubleExtra(SoilDataCollectionDetailActivity.LONGITUDE, 0.0)
        val soilDetailLocation = LatLng(latitude, longitude)
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.addMarker(MarkerOptions().position(soilDetailLocation).title("Selected Soil"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(soilDetailLocation, 15f))
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
}