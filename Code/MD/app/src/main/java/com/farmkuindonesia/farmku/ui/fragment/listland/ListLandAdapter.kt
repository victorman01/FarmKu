package com.farmkuindonesia.farmku.ui.fragment.listland

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmkuindonesia.farmku.databinding.DaftarLahanLayoutBinding
import com.farmkuindonesia.farmku.database.model.Dummy

class ListLandAdapter(private val listLand: ArrayList<Dummy>): RecyclerView.Adapter<ListLandAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListLandAdapter.ViewHolder {
        val binding = DaftarLahanLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listLand.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, date, photo) = listLand[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .into(holder.imgLand)
        holder.txtLocLand.text = name
        holder.txtDateLand.text = date
    }
    inner class ViewHolder(binding: DaftarLahanLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val txtLocLand: TextView = binding.txtLocLand
        val txtDateLand: TextView = binding.txtDateLand
        val imgLand: ImageView = binding.imgLand
    }
}