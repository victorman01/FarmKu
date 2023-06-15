package com.farmkuindonesia.farmku.ui.soildatacollection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
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