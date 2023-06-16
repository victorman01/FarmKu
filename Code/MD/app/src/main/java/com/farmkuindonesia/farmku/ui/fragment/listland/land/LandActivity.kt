package com.farmkuindonesia.farmku.ui.fragment.listland.land

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TableRow
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.farmkuindonesia.farmku.BuildConfig
import com.farmkuindonesia.farmku.database.responses.ItemData
import com.farmkuindonesia.farmku.databinding.ActivityLandBinding
import com.farmkuindonesia.farmku.ui.ViewModelFactory
import com.farmkuindonesia.farmku.ui.fragment.listland.addmeasurement.AddMeasurementActivity

class LandActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandBinding
    private lateinit var landActivityViewModel: LandViewModel
    private lateinit var viewModelFac: ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModelFac = ViewModelFactory.getInstance(this)
        landActivityViewModel =
            ViewModelProvider(this, viewModelFac)[LandViewModel::class.java]

        val userId = landActivityViewModel.getUserId().id.toString()
        val idLand = intent.getStringExtra(IDLAND)
        val nameLand = intent.getStringExtra(NAMELAND)
        val latString = intent.getStringExtra(LATLAND)
        val longString = intent.getStringExtra(LONLAND)
        val lat = latString?.toDouble()
        val long = longString?.toDouble()

        var measurementList: List<ItemData?>

        binding.txtLandName.text = nameLand
        measurementList = listOf()

        landActivityViewModel.getWeatherData(lat, long)
        landActivityViewModel.getMeasurement(userId)

        landActivityViewModel.measurementData.observe(this) {
            if (it != null) {
                measurementList = it
            }
        }

        landActivityViewModel.weatherData.observe(this) {
            binding.apply {

                val adapter = LandMeasurementAdapter(measurementList)
                rvMeasurement.layoutManager = LinearLayoutManager(this@LandActivity)
                rvMeasurement.adapter = adapter
                val windSpeed = it.wind?.speed?.toString() + " m/s"
                val temp = it.main?.temp?.toString() + " \u2103"
                val humidity = it.main?.humidity?.toString() + "%"
                val visibility = it.visibility?.toString() + " m"
                val weatherIcon = BuildConfig.URL_ICON_WEATHER + it.weather?.get(0)?.icon + ".png"

                txtWindSpeed.text = windSpeed
                txtTemperature.text = temp
                txtKelembaban.text = humidity
                txtPointOfView.text = visibility
                Glide.with(this@LandActivity).load(weatherIcon).into(imgWeather)
                fabAddMesurement.setOnClickListener {
                    val intent = Intent(this@LandActivity, AddMeasurementActivity::class.java)
                    intent.putExtra(AddMeasurementActivity.IDLAND, idLand)
                    intent.putExtra(AddMeasurementActivity.NAMELAND, nameLand)
                    intent.putExtra(AddMeasurementActivity.LATLAND, lat)
                    intent.putExtra(AddMeasurementActivity.LONLAND, long)
                    startActivity(intent)
                }
            }
        }

        val data = listOf(
            arrayOf("08:00", "Tempat A", "10", "15", "123"),
            arrayOf("09:00", "Tempat B", "5", "8", "123"),
            arrayOf("10:00", "Tempat C", "7", "12", "123")
        )

        for (item in data) {
            val tableRow = TableRow(this)

            val textView1 = TextView(this)
            textView1.text = item[0]
            textView1.setPadding(8, 8, 8, 8)
            textView1.gravity = Gravity.CENTER
            tableRow.addView(textView1)

            val textView2 = TextView(this)
            textView2.text = item[1]
            textView2.setPadding(8, 8, 8, 8)
            textView2.gravity = Gravity.CENTER
            tableRow.addView(textView2)

            val textView3 = TextView(this)
            textView3.text = item[2]
            textView3.setPadding(8, 8, 8, 8)
            textView3.gravity = Gravity.CENTER
            tableRow.addView(textView3)

            val textView4 = TextView(this)
            textView4.text = item[3]
            textView4.setPadding(8, 8, 8, 8)
            textView4.gravity = Gravity.CENTER
            tableRow.addView(textView4)

            val textView5 = TextView(this)
            textView5.text = item[4]
            textView5.setPadding(8, 8, 8, 8)
            textView5.gravity = Gravity.CENTER
            tableRow.addView(textView5)

            binding.tableLayout.addView(tableRow)
        }
    }

    companion object {
        const val IDLAND = "IDLAND"
        const val NAMELAND = "NAMELAND"
        const val LATLAND = "LATLAND"
        const val LONLAND = "LONLAND"
    }
}