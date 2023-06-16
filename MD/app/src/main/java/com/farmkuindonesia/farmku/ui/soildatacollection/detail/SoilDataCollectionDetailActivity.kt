package com.farmkuindonesia.farmku.ui.soildatacollection.detail

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.farmkuindonesia.farmku.databinding.ActivitySoilDataCollectionDetailBinding
import com.farmkuindonesia.farmku.ui.soildatacollection.detail.maps.SoilDataCollectionDetailMapsActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class SoilDataCollectionDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivitySoilDataCollectionDetailBinding

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoilDataCollectionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Soil Data Detail"

        mapView = binding.mapViewDetail
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        val latitude = intent.getDoubleExtra(LATITUDE, 0.0)
        val longitude = intent.getDoubleExtra(LONGITUDE, 0.0)

        Glide.with(this)
            .asBitmap()
            .load(intent.getStringExtra(IMAGE))
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    binding.imgSoilDataDetail.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Do nothing
                }
            })

        binding.apply {
            txtNamaVarietasDetail.text = intent.getStringExtra(NAMA_VARIETAS)
            txtNSoilDataDetail.text = intent.getDoubleExtra(N, 0.0).toString()
            txtPSoilDataDetail.text = intent.getDoubleExtra(P, 0.0).toString()
            txtKSoilDataDetail.text = intent.getDoubleExtra(K, 0.0).toString()
            txtPHSoilDataDetail.text = intent.getDoubleExtra(PH, 0.0).toString()
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

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        val latitude = intent.getDoubleExtra(LATITUDE, 0.0)
        val longitude = intent.getDoubleExtra(LONGITUDE, 0.0)

        // Customize the map settings and markers as needed
        googleMap.uiSettings.isZoomControlsEnabled = true

        val markerOptions = MarkerOptions()
            .position(LatLng(latitude, longitude))
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
        const val NAMA_VARIETAS = "NAMA_VARIETAS"
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