package com.farmkuindonesia.farmku.ui.fragment.listland.addmeasurement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.databinding.ActivityAddMeasurementBinding
import com.farmkuindonesia.farmku.databinding.ActivitySoilDataCollectionDetailBinding
import com.farmkuindonesia.farmku.ui.fragment.listland.land.LandActivity
import com.farmkuindonesia.farmku.ui.soildatacollection.detail.SoilDataCollectionDetailActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class AddMeasurementActivity : AppCompatActivity() , OnMapReadyCallback {
    private lateinit var binding: ActivityAddMeasurementBinding
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMeasurementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        mapView = binding.mapViewDetail
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val idLand = intent.getStringExtra(IDLAND)
        val nameLand = intent.getStringExtra(NAMELAND)
        val latitude = intent.getStringExtra(LATLAND)?.toDouble()
        val longitude = intent.getStringExtra(LONLAND)?.toDouble()

        googleMap.uiSettings.isZoomControlsEnabled = true

        val markerOptions = MarkerOptions()
            .position(LatLng(latitude!!, longitude!!))
            .title("Selected Soil")
        googleMap.addMarker(markerOptions)

        // Move the camera to the marker position
        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(latitude, longitude))
            .zoom(12.0f)
            .build()
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    companion object{
        const val IDLAND = "IDLAND"
        const val NAMELAND = "NAMELAND"
        const val LATLAND = "LATLAND"
        const val LONLAND = "LONLAND"
    }
}