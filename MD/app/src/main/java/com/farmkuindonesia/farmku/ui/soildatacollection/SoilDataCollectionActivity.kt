package com.farmkuindonesia.farmku.ui.soildatacollection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.database.responses.SoilDataCollectionResponseItem
import com.farmkuindonesia.farmku.databinding.ActivitySoilDataCollectionBinding
import com.farmkuindonesia.farmku.ui.ViewModelFactory
import com.farmkuindonesia.farmku.ui.soildatacollection.add.AddSoilDataActivity

class SoilDataCollectionActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySoilDataCollectionBinding
    private lateinit var soilDataCollectionViewModel: SoilDataCollectionViewModel
    private lateinit var viewModelFac: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoilDataCollectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Data Tanah"

        viewModelFac = ViewModelFactory.getInstance(this)
        soilDataCollectionViewModel =
            ViewModelProvider(this, viewModelFac)[SoilDataCollectionViewModel::class.java]

        var data: List<SoilDataCollectionResponseItem>
        soilDataCollectionViewModel.getSoilData()
        soilDataCollectionViewModel.soilData.observe(this) {
            data = it
            val adapter = SoilDataCollectionAdapter(data)
            binding.rvSoilData.adapter = adapter
            binding.rvSoilData.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }

        binding.fabAddSoilData.setOnClickListener{
            val intent = Intent(this, AddSoilDataActivity::class.java)
            startActivity(intent)
        }
        soilDataCollectionViewModel.messages.observe(this) { event ->
            event.getContentIfNotHandled()?.let { text ->
                showMessage(text)
            }
        }
        soilDataCollectionViewModel.isLoading.observe(this@SoilDataCollectionActivity) {
            showLoading(it)
        }
    }

    private fun showMessage(msg: String) {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container))
        val textView = layout.findViewById<TextView>(R.id.custom_toast_text)
        textView.text = msg
        val toast = Toast(this@SoilDataCollectionActivity)
        toast.view = layout
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.CENTER, 0, 800)
        toast.show()
    }
    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
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