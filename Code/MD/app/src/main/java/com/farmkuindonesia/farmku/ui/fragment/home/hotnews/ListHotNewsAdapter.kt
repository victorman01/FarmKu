package com.farmkuindonesia.farmku.ui.fragment.home.hotnews

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.farmkuindonesia.farmku.databinding.HotNewsLayoutBinding

class ListHotNewsAdapter(private val listNews: ArrayList<Dummy>): RecyclerView.Adapter<ListHotNewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = HotNewsLayoutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (name, date, photo) = listNews[position]
        holder.imgNews.setImageResource(photo)
        holder.txtTitleNews.text = name
        holder.txtDateNews.text = date
    }

    override fun getItemCount(): Int = listNews.size

    inner class ViewHolder(binding: HotNewsLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgNews: ImageView = binding.imgHotNews
        val txtTitleNews: TextView = binding.txtTitleHotNews
        val txtDateNews: TextView = binding.txtDateHotNews
    }
}