package com.ubaya.testdeploy

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.create
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.*
import java.text.SimpleDateFormat
import java.util.*


private const val FILENAME_FORMAT = "dd-MMM-yyyy"
private const val MAXIMAL_SIZE = 1000000

class MainActivity : AppCompatActivity() {
    val REQUEST_IMAGE_CAPTURE = 3
    val REQUEST_SELECT_GALERI = 4
    private lateinit var preprocessViewModel: PreprocessViewModel
    private lateinit var viewModelFactory: ViewModelFactory
    private var imageMultipart: MultipartBody.Part? = null
    lateinit var imageBytes:ByteArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModelFactory = ViewModelFactory.getInstance(this)
        preprocessViewModel = ViewModelProvider(this, viewModelFactory)[PreprocessViewModel::class.java]
        val btnPredict = findViewById<Button>(R.id.btnPredict)

        btnPredict.setOnClickListener {
//            val requestBody: RequestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), imageBytes)
//            val imagePart: MultipartBody.Part = MultipartBody.Part.createFormData("image", "image.jpg", requestBody)
            imageMultipart?.let { preprocessViewModel.result(it) }
            preprocessViewModel.preprocess.observe(this){
                Toast.makeText(this, "Result: " + it.result + " and Confidence: " +
                        it.confidence, Toast.LENGTH_LONG).show()
            }
        }

        val fabPhoto = findViewById<FloatingActionButton>(R.id.fabPhoto)
        fabPhoto.setOnClickListener{
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), REQUEST_IMAGE_CAPTURE)
            } else {
                val pickImageIntent = Intent(Intent.ACTION_GET_CONTENT)
                pickImageIntent.type = "image/*"
                startActivityForResult(pickImageIntent, REQUEST_SELECT_GALERI)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_IMAGE_CAPTURE){
            data?.extras?.let {
                val imgPhoto = findViewById<ImageView>(R.id.imgPhoto)
                imgPhoto.setImageBitmap(it.get("data") as Bitmap)
                val imageFile = convertBitmapToFile(it.get("data") as Bitmap)
                val requestImageFile = imageFile?.asRequestBody("image/jpeg".toMediaType())
                imageMultipart = MultipartBody.Part.createFormData(
                    "photo",
                    imageFile?.name,
                    requestImageFile!!
                )
            }
        } else if(requestCode == REQUEST_SELECT_GALERI) {
            val selectedImageUri = data?.data
            val imgPhoto = findViewById<ImageView>(R.id.imgPhoto)
            val bitmap2 : Bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selectedImageUri)
            imgPhoto.setImageBitmap(bitmap2)

//            val baos = ByteArrayOutputStream()
//            bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, baos)
//            imageBytes = baos.toByteArray()

            val imageFile = convertBitmapToFile(bitmap2)
            val requestImageFile = imageFile?.asRequestBody("image/jpeg".toMediaType())
            imageMultipart = MultipartBody.Part.createFormData(
                "photo",
                imageFile?.name,
                requestImageFile!!
            )
        }
    }
    private fun convertBitmapToFile(bitmap: Bitmap): File? {
        val file = File(cacheDir, "image_temp.jpg")
        try {
            val fos = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    fun uriToFile(selectedImg: Uri, context: Context): File {
        val contentResolver: ContentResolver = context.contentResolver
        val myFile = createCustomTempFile(context)
        val inputStream = contentResolver.openInputStream(selectedImg) as InputStream
        val outputStream: OutputStream = FileOutputStream(myFile)
        val buf = ByteArray(1024)
        var len: Int
        while (inputStream.read(buf).also { len = it } > 0) outputStream.write(buf, 0, len)
        outputStream.close()
        inputStream.close()
        return myFile
    }

    val timeStamp: String = SimpleDateFormat(
        FILENAME_FORMAT, Locale.US
    ).format(System.currentTimeMillis())

    fun createCustomTempFile(context: Context): File {
        val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(timeStamp, ".jpg", storageDir)
    }

    fun takePicture(){
        val intent = Intent()
        intent.action = MediaStore.ACTION_IMAGE_CAPTURE
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            REQUEST_IMAGE_CAPTURE ->{
                if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    takePicture()
                } else {
                    Toast.makeText(this, "You Must Grant Permission to Access the Camera", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}