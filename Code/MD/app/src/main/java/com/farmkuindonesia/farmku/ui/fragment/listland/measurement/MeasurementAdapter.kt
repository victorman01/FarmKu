package com.farmkuindonesia.farmku.ui.fragment.listland.measurement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farmkuindonesia.farmku.database.responses.RecordsItem
import com.farmkuindonesia.farmku.databinding.ListMeasurementLayoutBinding

class MeasurementAdapter(private val recordList: List<RecordsItem?>) :
    RecyclerView.Adapter<MeasurementAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListMeasurementLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = recordList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val createdAt = recordList[position]?.createdAt.toString()
        holder.binding.txtNameMeasurement.text = "#${position+1}"
        holder.binding.txtDateMeasurement.text = createdAt
    }

    inner class ViewHolder(val binding: ListMeasurementLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}