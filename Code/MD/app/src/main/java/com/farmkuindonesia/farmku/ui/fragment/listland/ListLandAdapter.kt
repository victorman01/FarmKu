package com.farmkuindonesia.farmku.ui.fragment.listland

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmkuindonesia.farmku.database.responses.LandItem
import com.farmkuindonesia.farmku.databinding.DaftarLahanLayoutBinding

class ListLandAdapter(private val listLand: List<LandItem?>): RecyclerView.Adapter<ListLandAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListLandAdapter.ViewHolder {
        val binding = DaftarLahanLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listLand.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtLocLand.text = listLand[position]?.name
        holder.txtDateLand.text = listLand[position]?.address?.village
    }
    inner class ViewHolder(binding: DaftarLahanLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val txtLocLand: TextView = binding.txtLocLand
        val txtDateLand: TextView = binding.txtDateLand
    }
}