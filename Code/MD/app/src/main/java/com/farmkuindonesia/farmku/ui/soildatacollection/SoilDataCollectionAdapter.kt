package com.farmkuindonesia.farmku.ui.soildatacollection

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.farmkuindonesia.farmku.database.responses.SoilDataCollectionResponseItem
import com.farmkuindonesia.farmku.databinding.SoilDataCollectionLayoutBinding
import com.farmkuindonesia.farmku.ui.soildatacollection.detail.SoilDataCollectionDetailActivity

class SoilDataCollectionAdapter(private val listData: List<SoilDataCollectionResponseItem>): RecyclerView.Adapter<SoilDataCollectionAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoilDataCollectionAdapter.ViewHolder {
        val binding = SoilDataCollectionLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val id = listData[position].id
        val n = listData[position].n
        val p = listData[position].p
        val k = listData[position].k
        val ph = listData[position].pH
        val long = listData[position].longitude
        val lat = listData[position].latitude
        val imgUrl = listData[position].image
        val desc = listData[position].description

        holder.txtNList.text = "N = $n"
        holder.txtPList.text = "P = $p"
        holder.txtKList.text = "K = $k"

        Glide.with(holder.itemView.context)
            .asBitmap()
            .load(imgUrl)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // Set the loaded image bitmap to the ImageView
                    holder.imgSoilDataCollection.setImageBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    // Optional: Handle any cleanup or placeholder display if needed
                }
            })

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, SoilDataCollectionDetailActivity::class.java)
            intent.putExtra(SoilDataCollectionDetailActivity.ID, id)
            intent.putExtra(SoilDataCollectionDetailActivity.N, n)
            intent.putExtra(SoilDataCollectionDetailActivity.P, p)
            intent.putExtra(SoilDataCollectionDetailActivity.K, k)
            intent.putExtra(SoilDataCollectionDetailActivity.PH, ph)
            intent.putExtra(SoilDataCollectionDetailActivity.LONGITUDE, long)
            intent.putExtra(SoilDataCollectionDetailActivity.LATITUDE, lat)
            intent.putExtra(SoilDataCollectionDetailActivity.IMAGE, imgUrl)
            intent.putExtra(SoilDataCollectionDetailActivity.DESCRIPTION, desc)
            holder.itemView.context.startActivity(intent)
        }
    }
    inner class ViewHolder(binding: SoilDataCollectionLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val imgSoilDataCollection: ImageView = binding.imgSoilDataCollection
        val txtNList: TextView = binding.txtNList
        val txtPList: TextView = binding.txtPList
        val txtKList: TextView = binding.txtKList
    }
}