package com.farmkuindonesia.farmku.ui.fragment.listland.addland

import android.R.attr
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.database.responses.AddLandResponse
import com.farmkuindonesia.farmku.database.responses.LocationAddLand
import com.farmkuindonesia.farmku.databinding.ActivityAddLandBinding
import com.farmkuindonesia.farmku.ui.ViewModelFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback


class AddLandActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityAddLandBinding
    private lateinit var viewModelFac: ViewModelFactory
    private lateinit var addLandViewModel: AddLandViewModel


    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap

    private lateinit var namesProvince: MutableList<String>
    private lateinit var idsProvince: MutableList<String>
    private lateinit var namesDistrict: MutableList<String>
    private lateinit var idsDistrict: MutableList<String>
    private lateinit var namesRegency: MutableList<String>
    private lateinit var idsRegency: MutableList<String>
    private lateinit var namesVillage: MutableList<String>
    private lateinit var idsVillage: MutableList<String>
    private lateinit var namesVariety: MutableList<String>
    private lateinit var idsVariety: MutableList<String>

    var selectedIdp = "0"
    var selectedIdd = "0"
    var selectedIdr = "0"
    var selectedIdv = "0"
    var selectedIdVariety = "0"

    var lng = 0.0
    var lat = 0.0

    private var addressSelected: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddLandBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModelFac = ViewModelFactory.getInstance(this)
        addLandViewModel = ViewModelProvider(this, viewModelFac)[AddLandViewModel::class.java]

        mapView = binding.mapViewLand
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        supportActionBar?.hide()

        binding.apply {
            spinnerRegency.isEnabled = false
            spinnerDistrict.isEnabled = false
            spinnerVillage.isEnabled = false
        }

        namesVariety = mutableListOf()
        namesVariety.add(getString(R.string.pilih_variety))
        idsVariety = mutableListOf()
        idsVariety.add("0")
        getVariety()

        binding.spinnerVariety.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedIdVariety = if (binding.spinnerVariety.selectedItemPosition != 0) {
                        idsVariety[position]
                    } else {
                        "0"
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        binding.spinnerProvince.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    namesDistrict = mutableListOf()
                    namesDistrict.add(getString(R.string.pilih_kabupaten_kota_anda))
                    idsDistrict = mutableListOf()
                    idsDistrict.add("0")

                    if (binding.spinnerProvince.selectedItemPosition != 0) {
                        selectedIdp = idsProvince[position]
                        getDistrict(selectedIdp)
                    } else {
                        selectedIdp = "0"
                    }
                    binding.spinnerDistrict.setSelection(0)

                    binding.spinnerDistrict.isEnabled = selectedIdp != "0"
                    binding.spinnerRegency.isEnabled = false
                    binding.spinnerVillage.isEnabled = false
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do Nothing
                }
            }

        binding.spinnerDistrict.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    namesRegency = mutableListOf()
                    namesRegency.add(getString(R.string.pilih_kecamatan_anda))
                    idsRegency = mutableListOf()
                    idsRegency.add("0")

                    if (binding.spinnerDistrict.selectedItemPosition != 0) {
                        selectedIdd = idsDistrict[position]
                        getRegency(selectedIdd)
                    } else {
                        selectedIdd = "0"
                    }
                    binding.spinnerRegency.setSelection(0)

                    binding.spinnerRegency.isEnabled = selectedIdd != "0"
                    binding.spinnerVillage.isEnabled = false
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do Nothing
                }
            }

        binding.spinnerRegency.onItemSelectedListener =
            object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    namesVillage = mutableListOf()
                    namesVillage.add(getString(R.string.pilih_desa_kelurahan_anda))
                    idsVillage = mutableListOf()
                    idsVillage.add("0")

                    binding.spinnerVillage.setSelection(0)
                    if (binding.spinnerRegency.selectedItemPosition != 0) {
                        selectedIdr = idsRegency[position]
                        getVillage(selectedIdr)
                    } else {
                        selectedIdr = "0"
                    }
                    binding.spinnerVillage.isEnabled = selectedIdr != "0"
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do Nothing
                }
            }

        binding.spinnerVillage.onItemSelectedListener =
            object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (binding.spinnerVillage.selectedItemPosition != 0) {
                        selectedIdv = idsVillage[position]
                    }
                    addressSelected = selectedIdv
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>?
                ) {
                    // Do Nothing
                }
            }

        setInitialSpinner(binding.spinnerProvince, getString(R.string.pilih_provinsi_anda))
        setInitialSpinner(binding.spinnerDistrict, getString(R.string.pilih_kabupaten_kota_anda))
        setInitialSpinner(binding.spinnerRegency, getString(R.string.pilih_kecamatan_anda))
        setInitialSpinner(binding.spinnerVillage, getString(R.string.pilih_desa_kelurahan_anda))
        setInitialSpinner(binding.spinnerVillage, getString(R.string.pilih_variety))

        binding.spinnerProvince.setSelection(0)
        addLandViewModel.getProvince().observe(this) { provinceList ->

            namesProvince = mutableListOf()
            namesProvince.add(getString(R.string.pilih_provinsi_anda))

            idsProvince = mutableListOf()
            idsProvince.add("0")

            provinceList?.forEach {
                namesProvince.add(it?.name.toString())
                idsProvince.add(it?.id.toString())
            }

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                namesProvince
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerProvince.adapter = adapter
        }

        binding.apply {
            btnAddLand.setOnClickListener {

                val name = txtNameLand.text.toString()
                val userId = addLandViewModel.getIdUser().id
                val varietySelected = selectedIdVariety
                val areaString = txtArea.text.toString()
                val area = areaString.toInt()
                val address = selectedIdv
                val location = LocationAddLand(lat, lng)

                val request = AddLandResponse(area,varietySelected,userId,name,address,location)

                if (userId != null) {
                    addLandViewModel.addNewLand(request)
                }
            }
        }
        addLandViewModel.messages.observe(this) { event ->
            event.getContentIfNotHandled()?.let { text ->
                showMessage(text)
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.setOnMapClickListener { latLng ->
            lat = latLng.latitude
            lng = latLng.longitude
        }
    }

    private fun showMessage(msg: String) {
        val inflater = layoutInflater
        val layout =
            inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container))
        val textView = layout.findViewById<TextView>(R.id.custom_toast_text)
        textView.text = msg
        val toast = Toast(this@AddLandActivity)
        toast.view = layout
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.CENTER, 0, 800)
        toast.show()
    }

    private fun getDistrict(selectedIdp: String) {
        addLandViewModel.getDistrict(selectedIdp)
            .observe(this@AddLandActivity) { districtList ->
                districtList?.forEach {
                    namesDistrict.add(it?.name.toString())
                    idsDistrict.add(it?.id.toString())
                }

                val districtAdapter = ArrayAdapter(
                    this@AddLandActivity,
                    android.R.layout.simple_spinner_item,
                    namesDistrict
                )
                districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerDistrict.adapter = districtAdapter

            }
    }

    private fun getRegency(selectedIdd: String) {
        addLandViewModel.getRegency(selectedIdd)
            .observe(this@AddLandActivity) { regencyList ->
                regencyList?.forEach {
                    namesRegency.add(it?.name.toString())
                    idsRegency.add(it?.id.toString())
                }

                val regencyAdapter = ArrayAdapter(
                    this@AddLandActivity,
                    android.R.layout.simple_spinner_item,
                    namesRegency
                )
                regencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerRegency.adapter = regencyAdapter

            }
    }

    private fun getVillage(selectedIdr: String) {
        addLandViewModel.getVillage(selectedIdr)
            .observe(this@AddLandActivity) { villageList ->

                villageList?.forEach {
                    namesVillage.add(it?.name.toString())
                    idsVillage.add(it?.id.toString())
                }

                val villageAdapter =
                    ArrayAdapter(
                        this@AddLandActivity,
                        android.R.layout.simple_spinner_item,
                        namesVillage
                    )
                villageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerVillage.adapter = villageAdapter
            }
    }

    private fun getVariety() {
        addLandViewModel.getVariety()
        addLandViewModel.varietyList.observe(this) { variety ->
            variety?.forEach {
                namesVariety.add(it.name.toString())
                idsVariety.add(it.id.toString())
            }
            val varietyAdapter =
                ArrayAdapter(
                    this@AddLandActivity,
                    android.R.layout.simple_spinner_item,
                    namesVariety
                )
            varietyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerVariety.adapter = varietyAdapter
        }
    }

    private fun setInitialSpinner(spinner: Spinner, hint: String) {
        val hints = mutableListOf(hint)
        val dadapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            hints
        )
        dadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = dadapter
    }
}