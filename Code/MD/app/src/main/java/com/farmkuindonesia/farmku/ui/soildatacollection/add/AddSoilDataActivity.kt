package com.farmkuindonesia.farmku.ui.soildatacollection.add

import android.Manifest
import android.content.Context
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
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.database.responses.ImageItem
import com.farmkuindonesia.farmku.database.responses.SoilDataCollectionResponseItem
import com.farmkuindonesia.farmku.databinding.ActivityAddSoilDataBinding
import com.farmkuindonesia.farmku.ui.ViewModelFactory
import com.farmkuindonesia.farmku.utils.createCustomTempFile
import com.farmkuindonesia.farmku.utils.reduceFileImage
import com.farmkuindonesia.farmku.utils.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AddSoilDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddSoilDataBinding
    private lateinit var currentPhotoPath: String
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener

    private lateinit var addSoilDataViewModel: AddSoilDataViewModel
    private lateinit var viewModelFac: ViewModelFactory

    private var getFile: File? = null
    private val REQUEST_CAMERA_PERMISSION = 100
    private val REQUEST_GALLERY_PERMISSION = 200
    private val REQUEST_LOCATION_PERMISSION = 1001

    private var name: String = ""
    private var n: Double = 0.0
    private var p: Double = 0.0
    private var k: Double = 0.0
    private var ph: Double = 0.0
    private var desc: String = ""
    private var long: Double = 0.0
    private var lat: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSoilDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.tambahkan_data_tanah_text)

        viewModelFac = ViewModelFactory.getInstance(this)
        addSoilDataViewModel = ViewModelProvider(this, viewModelFac)[AddSoilDataViewModel::class.java]

        getCurrentLocation()

        addSoilDataViewModel.message.observe(this) { event ->
            event.getContentIfNotHandled()?.let { text ->
                showMessage(text)
            }
        }

        binding.txtDescriptionAddSoilData.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length <= 200) {
                    binding.txtCharCounterAddSoil.text = "${s.length}/200"
                    if (s.length == 200) {
                        Toast.makeText(
                            this@AddSoilDataActivity,
                            getString(R.string.maksimum_karakter_tercapai_text),
                            Toast.LENGTH_SHORT
                        ).show()
                        binding.txtDescriptionAddSoilData.clearFocus()
                        hideKeyboard(this@AddSoilDataActivity)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }

        })

        binding.btnCameraAddSoil.setOnClickListener{
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
        binding.btnGalleryAddSoil.setOnClickListener{
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

        binding.btnAddDataSoil.setOnClickListener{
            if (getFile != null) {
                if (binding.txtDescriptionAddSoilData.text.isNotEmpty() && binding.txtNAdd.text.isNotEmpty() && binding.txtPAdd.text.isNotEmpty() && binding.txtKAdd.text.isNotEmpty() && binding.txtPHAdd.text.isNotEmpty() && binding.txtDescriptionAddSoilData.text.isNotEmpty()){
                    binding.apply {
                        name = txtNamaVarietasAddSoil.text.toString()
                        n = txtNAdd.text.toString().toDouble()
                        p = txtPAdd.text.toString().toDouble()
                        k = txtKAdd.text.toString().toDouble()
                        ph = txtPHAdd.text.toString().toDouble()
                        desc = txtDescriptionAddSoilData.text.toString()
                    }

                    val file = reduceFileImage(getFile as File)
                    val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
                    val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                        "Image",
                        file.name,
                        requestImageFile
                    )
                    addSoilDataViewModel.send(namaVarietas = name, n = n, p = p, k = k, pH = ph, longitude = long, latitude = lat, image = imageMultipart, description = desc)

                    finish()

                } else{
                  Toast.makeText(this, "Harap isi seluruh data", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(
                    this@AddSoilDataActivity,
                    getString(R.string.Silahkan_ambil_foto_terlebih_dahulu),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)
            getFile = myFile
            val bitmap = BitmapFactory.decodeFile(myFile.path)
            binding.imgAddSoilData.setImageBitmap(bitmap)
        }
    }
    private fun showMessage(msg: String) {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container))
        val textView = layout.findViewById<TextView>(R.id.custom_toast_text)
        textView.text = msg
        val toast = Toast(this@AddSoilDataActivity)
        toast.view = layout
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.CENTER, 0, 800)
        toast.show()
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@AddSoilDataActivity,
                "com.farmkuindonesia.farmku.ui",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val selectedImageUri: Uri = it.data?.data as Uri
            val myFile = uriToFile(selectedImageUri, this)
            getFile = myFile
            binding.imgAddSoilData.setImageURI(selectedImageUri)
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

    fun hideKeyboard(context: Context) {
        val view: View? = this.currentFocus
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
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
        const val TAG = "DiseaseDetectionActivity"
    }
}