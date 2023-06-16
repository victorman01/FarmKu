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

class AddMeasurementActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityAddMeasurementBinding
    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    private var idLand = ""
    private var nameLand = ""
    private var latitude = 0.0
    private var longitude = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddMeasurementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        idLand = intent.getStringExtra(IDLAND).toString()
        nameLand = intent.getStringExtra(NAMELAND).toString()
        latitude = intent.getStringExtra(LATLAND)?.toDouble() ?: 0.0
        longitude = intent.getStringExtra(LONLAND)?.toDouble()?: 0.0

        mapView = binding.mapViewDetail
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        googleMap.uiSettings.isZoomControlsEnabled = true

        val markerOptions = MarkerOptions()
            .position(LatLng(latitude, longitude))
            .title("Selected Soil")
        googleMap.addMarker(markerOptions)

        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(latitude, longitude))
            .zoom(12.0f)
            .build()
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }

    companion object {
        const val IDLAND = "IDLAND"
        const val NAMELAND = "NAMELAND"
        const val LATLAND = "LATLAND"
        const val LONLAND = "LONLAND"
    }
}