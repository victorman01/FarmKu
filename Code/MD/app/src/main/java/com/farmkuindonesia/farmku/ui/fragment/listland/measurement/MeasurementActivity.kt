package com.farmkuindonesia.farmku.ui.fragment.listland.measurement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmkuindonesia.farmku.database.responses.RecordsItem
import com.farmkuindonesia.farmku.databinding.ActivityMeasurementBinding
import com.farmkuindonesia.farmku.ui.ViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MeasurementActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMeasurementBinding
    private lateinit var measurementViewModel: MeasurementViewModel
    private lateinit var viewModelFac: ViewModelFactory
    private var idMeasurement = ""
    private var latitude = 0.0
    private var longitude = 0.0
    private var recordList: List<RecordsItem?>? = listOf()

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeasurementBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.txtdeviceID.text = "Device: Not Found"
        supportActionBar?.hide()

        viewModelFac = ViewModelFactory.getInstance(this)
        measurementViewModel =
            ViewModelProvider(this, viewModelFac)[MeasurementViewModel::class.java]

        idMeasurement = intent.getStringExtra(IDMEASUREMENT).toString()

        measurementViewModel.getRecord(idMeasurement)
        measurementViewModel.record.observe(this) { record ->
            recordList = record
            if(recordList.isNullOrEmpty()){
                binding.txtNullData.visibility = View.VISIBLE
            }else{
                val adapter = MeasurementAdapter(recordList ?: listOf())
                binding.rvRecords.layoutManager = LinearLayoutManager(this)
                binding.rvRecords.adapter = adapter
                binding.txtNullData.visibility = View.GONE
            }

        }


        mapView = binding.mapViewDetail
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        latitude = intent.getDoubleExtra(LANDLT,0.0)
        longitude = intent.getDoubleExtra(LANDLNG,0.0)
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
        const val IDMEASUREMENT = "IDMEASUREMENT"
        const val LANDLNG = "LANDLNG"
        const val LANDLT = "LANDLT"
    }
}