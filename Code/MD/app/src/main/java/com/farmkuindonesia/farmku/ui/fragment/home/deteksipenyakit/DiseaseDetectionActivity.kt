package com.farmkuindonesia.farmku.ui.fragment.home.deteksipenyakit

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.databinding.ActivityDiseaseDetectionBinding
import com.farmkuindonesia.farmku.ui.ViewModelFactory
import com.farmkuindonesia.farmku.utils.createCustomTempFile
import com.farmkuindonesia.farmku.utils.reduceFileImage
import com.farmkuindonesia.farmku.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class DiseaseDetectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDiseaseDetectionBinding
    private lateinit var diseaseDetectionViewModel: DiseaseDetectionViewModel
    private lateinit var viewModelFac: ViewModelFactory
    private lateinit var currentPhotoPath: String
    private var getFile: File? = null
    private val REQUEST_CAMERA_PERMISSION = 100
    private val REQUEST_GALLERY_PERMISSION = 200
    private val REQUEST_LOCATION_PERMISSION = 1001

    private var long: Double? = null
    private var lat: Double? = null

    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiseaseDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        actionBar?.title = "Deteksi Penyakit"

        viewModelFac = ViewModelFactory.getInstance(this)
        diseaseDetectionViewModel =
            ViewModelProvider(this, viewModelFac)[DiseaseDetectionViewModel::class.java]

        binding.buttonAddCamera.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                startTakePhoto()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_CAMERA_PERMISSION
                )
            }
        }

        binding.buttonAddGallery.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                gallery()
            } else {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_GALLERY_PERMISSION
                )
            }
        }

//        addStoryBinding.checkBoxLocation.setOnCheckedChangeListener { buttonView, isChecked ->
//            if (isChecked) {
//                getCurrentLocation()
//            } else {
//                locationManager.removeUpdates(locationListener)
//            }
//        }

        binding.buttonDetect.setOnClickListener {
            if (getFile != null) {
                val file = reduceFileImage(getFile as File)
                val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
                val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo",
                    file.name,
                    requestImageFile
                )
                diseaseDetectionViewModel.result(imageMultipart)
                diseaseDetectionViewModel.preprocess.observe(this) { result ->
                    val confidencePercentage: Int = (result.confidence * 100).toInt()
                    binding.resultText.text = getString(R.string.result_text, result.result)
                    binding.confidenceText.text =
                        getString(R.string.confidence_text, confidencePercentage)
                }
            } else {
                Toast.makeText(
                    this@DiseaseDetectionActivity,
                    getString(R.string.null_picture_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
            diseaseDetectionViewModel.message.observe(this) { event ->
                event.getContentIfNotHandled()?.let { text ->
                    showMessage(text)
                }
            }
            diseaseDetectionViewModel.isLoading.observe(this@DiseaseDetectionActivity) {
                if (it){
                    binding.apply {
                        buttonDetect.isEnabled = false
                        buttonAddCamera.isEnabled = false
                        buttonAddGallery.isEnabled = false
                    }
                }
                else{
                    binding.apply{
                        binding.buttonDetect.isEnabled = true
                        binding.buttonAddCamera.isEnabled = true
                        binding.buttonAddGallery.isEnabled = true
                    }
                }
                showLoading(it)
            }
        }
    }

    private fun showMessage(msg: String) {
        val inflater = layoutInflater
        val layout =
            inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container))
        val textView = layout.findViewById<TextView>(R.id.custom_toast_text)
        textView.text = msg
        val toast = Toast(this@DiseaseDetectionActivity)
        toast.view = layout
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.CENTER, 0, 800)
        toast.show()
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile
            val bitmap = BitmapFactory.decodeFile(myFile.path)
            binding.ivAddPhoto.setImageBitmap(bitmap)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val selectedImageUri: Uri = it.data?.data as Uri
            val myFile = uriToFile(selectedImageUri, this)
            getFile = myFile
            binding.ivAddPhoto.setImageURI(selectedImageUri)
        }
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@DiseaseDetectionActivity,
                "com.farmkuindonesia.farmku.ui.fragment.home.deteksipenyakit",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private fun gallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"

        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private fun getCurrentLocation() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                long = location.longitude
                lat = location.latitude
                Log.d("DiseaseDetectionActivity", "Longitude: $long, Latitude: $lat")
            }

            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}
        }
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
            return
        }
        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            0,
            0f,
            locationListener
        )
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startTakePhoto()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }

            REQUEST_GALLERY_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    gallery()
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
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
}