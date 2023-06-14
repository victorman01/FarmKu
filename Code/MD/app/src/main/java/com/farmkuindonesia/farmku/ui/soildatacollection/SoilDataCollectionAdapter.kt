package com.farmkuindonesia.farmku.ui.soildatacollection

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.farmkuindonesia.farmku.database.responses.SoilDataCollectionResponseItem
import com.farmkuindonesia.farmku.databinding.SoilDataCollectionLayoutBinding

class SoilDataCollectionAdapter(private val listData: List<SoilDataCollectionResponseItem>): RecyclerView.Adapter<SoilDataCollectionAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoilDataCollectionAdapter.ViewHolder {
        val binding = SoilDataCollectionLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtNList.text = "N = ${listData[position].n.toString()}"
        holder.txtPList.text = "P = ${listData[position].p.toString()}"
        holder.txtKList.text = "K = ${listData[position].k.toString()}"
    }
    inner class ViewHolder(binding: SoilDataCollectionLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgSoilDataCollection: ImageView = binding.imgSoilDataCollection
        val txtNList: TextView = binding.txtNList
        val txtPList: TextView = binding.txtPList
        val txtKList: TextView = binding.txtKList
    }
}