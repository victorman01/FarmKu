package com.farmkuindonesia.farmku.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.farmkuindonesia.farmku.databinding.NewsLayoutBinding

class ListNewsAdapter(private val listNews: ArrayList<Dummy>): RecyclerView.Adapter<ListNewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder{
        val binding = NewsLayoutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, date, photo) = listNews[position]
        holder.imgNews.setImageResource(photo)
        holder.txtTitleNews.text = name
        holder.txtDateNews.text = date
    }

    override fun getItemCount(): Int = listNews.size

    inner class ViewHolder(binding: NewsLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgNews: ImageView = binding.imgNews
        val txtTitleNews: TextView = binding.txtTitleNews
        val txtDateNews: TextView = binding.txtDateNews
    }
}