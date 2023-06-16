package com.farmkuindonesia.farmku.ui.fragment.listland.land

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.databinding.ActivityLandBinding
import com.farmkuindonesia.farmku.ui.ViewModelFactory

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

        val nameLand = intent.getStringExtra(NAMELAND)
        binding.txtLandName.text = nameLand

        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)

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

            tableLayout.addView(tableRow)
        }
    }

    companion object {
        const val IDLAND = "IDLAND"
        const val NAMELAND = "NAMELAND"
    }
}