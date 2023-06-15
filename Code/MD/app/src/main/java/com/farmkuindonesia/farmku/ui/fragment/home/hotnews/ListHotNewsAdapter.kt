package com.farmkuindonesia.farmku.ui.fragment.home.hotnews

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmkuindonesia.farmku.database.model.Dummy
import com.farmkuindonesia.farmku.databinding.HotNewsLayoutBinding

class ListHotNewsAdapter(private val listNews: ArrayList<Dummy>) : RecyclerView.Adapter<ListHotNewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HotNewsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = listNews[position]
        holder.bind(newsItem)
    }

    override fun getItemCount(): Int = listNews.size

    inner class ViewHolder(private val binding: HotNewsLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        private val imgNews: ImageView = binding.imgHotNews
        private val txtTitleNews: TextView = binding.txtTitleHotNews
        private val txtDateNews: TextView = binding.txtDateHotNews

        fun bind(newsItem: Dummy) {
            Glide.with(itemView.context)
                .load(newsItem.photo)
                .into(imgNews)
            txtTitleNews.text = newsItem.name
            txtDateNews.text = newsItem.date
        }
    }
}
