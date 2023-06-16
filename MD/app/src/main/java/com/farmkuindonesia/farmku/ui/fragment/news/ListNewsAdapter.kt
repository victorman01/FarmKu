package com.farmkuindonesia.farmku.ui.fragment.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmkuindonesia.farmku.databinding.NewsLayoutBinding
import com.farmkuindonesia.farmku.database.model.Dummy

class ListNewsAdapter(private val listNews: ArrayList<Dummy>) : RecyclerView.Adapter<ListNewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = listNews[position]

        with(holder.binding) {
            Glide.with(root.context)
                .load(newsItem.photo)
                .into(imgNewsNews)

            txtTitleNews.text = newsItem.name
            txtDateNews.text = newsItem.date
        }
    }

    override fun getItemCount(): Int = listNews.size

    inner class ViewHolder(val binding: NewsLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}
