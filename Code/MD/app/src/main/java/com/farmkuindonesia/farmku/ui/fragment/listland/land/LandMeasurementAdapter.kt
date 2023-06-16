package com.farmkuindonesia.farmku.ui.fragment.listland.land

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farmkuindonesia.farmku.database.responses.ItemData
import com.farmkuindonesia.farmku.databinding.ListMeasurementLayoutBinding
import com.farmkuindonesia.farmku.ui.fragment.listland.measurement.MeasurementActivity


class LandMeasurementAdapter(private val listMeasurement: List<ItemData?>) :
    RecyclerView.Adapter<LandMeasurementAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListMeasurementLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = listMeasurement[position]?.updatedAt
        val lon = listMeasurement[position]?.land?.location?.lon
        val lat = listMeasurement[position]?.land?.location?.lat
        holder.binding.txtDateMeasurement.text = date
        holder.binding.txtNameMeasurement.text = "Measurement #${position+1}"
        holder.itemView.setOnClickListener{
            val intent = Intent(it.context,MeasurementActivity::class.java)
            intent.putExtra(MeasurementActivity.IDMEASUREMENT,listMeasurement[position]?.id)
            intent.putExtra(MeasurementActivity.LANDLNG,lon.toString())
            intent.putExtra(MeasurementActivity.LANDLT,lat.toString())
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int = listMeasurement.size


    inner class ViewHolder(val binding: ListMeasurementLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}
