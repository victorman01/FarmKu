package com.farmkuindonesia.farmku.ui.fragment.listland.land

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farmkuindonesia.farmku.database.responses.ItemData
import com.farmkuindonesia.farmku.databinding.ListMeasurementLayoutBinding


class LandMeasurementAdapter(private val listMeasurement: List<ItemData?>) :
    RecyclerView.Adapter<LandMeasurementAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListMeasurementLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = listMeasurement[position]?.createdAt
        holder.binding.txtDateMeasurement.text = date
        holder.binding.txtNameMeasurement.text = "Measurement #${position}"
    }

    override fun getItemCount(): Int = listMeasurement.size


    inner class ViewHolder(val binding: ListMeasurementLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}
