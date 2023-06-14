package com.farmkuindonesia.farmku.ui.fragment.news

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmkuindonesia.farmku.databinding.NewsLayoutBinding
import com.farmkuindonesia.farmku.database.model.Dummy

class ListNewsAdapter(private val listNews: ArrayList<Dummy>): RecyclerView.Adapter<ListNewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsLayoutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, date, photo) = listNews[position]
        Glide.with(holder.itemView.context)
            .load(photo)
            .into(holder.imgNews)
        holder.txtTitleNews.text = name
        holder.txtDateNews.text = date
    }

    override fun getItemCount(): Int = listNews.size

    inner class ViewHolder(binding: NewsLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgNews: ImageView = binding.imgNewsNews
        val txtTitleNews: TextView = binding.txtTitleNews
        val txtDateNews: TextView = binding.txtDateNews
    }
}