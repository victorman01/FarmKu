package com.farmkuindonesia.farmku.ui.soildatacollection

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.farmkuindonesia.farmku.database.responses.SoilDataCollectionResponseItem
import com.farmkuindonesia.farmku.databinding.SoilDataCollectionLayoutBinding
import com.farmkuindonesia.farmku.ui.soildatacollection.detail.SoilDataCollectionDetailActivity
import org.json.JSONArray
import org.json.JSONObject

class SoilDataCollectionAdapter(private val listData: List<SoilDataCollectionResponseItem>) :
    RecyclerView.Adapter<SoilDataCollectionAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SoilDataCollectionAdapter.ViewHolder {
        val binding = SoilDataCollectionLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listData.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = listData[position]
        val imageList = data.image
        var imageUrl = ""

        if (imageList != null) {
            if (imageList.isNotEmpty()) {
                val imageObject = imageList[0]
                imageUrl = "https://farmku-collection-hwmomiroxq-et.a.run.app/${imageObject?.destination + imageObject?.filename}"

                Glide.with(holder.itemView.context)
                    .asBitmap()
                    .load(imageUrl)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                            holder.imgSoilDataCollection.setImageBitmap(resource)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            // Do nothing
                        }
                    })
            }
        }

        val id = data.id
        val name = data.namaVarietas
        val n = data.n
        val p = data.p
        val k = data.k
        val ph = data.pH
        val long = data.longitude
        val lat = data.latitude
        val desc = data.description

        holder.txtNamaVarietasList.text = name
        holder.txtNList.text = "N = $n"
        holder.txtPList.text = "P = $p"
        holder.txtKList.text = "K = $k"
        holder.txtPHList.text = "pH = $ph"

        holder.itemView.setOnClickListener {
            val intent =
                Intent(holder.itemView.context, SoilDataCollectionDetailActivity::class.java)
            intent.apply {
                putExtra(SoilDataCollectionDetailActivity.ID, id)
                putExtra(SoilDataCollectionDetailActivity.NAMA_VARIETAS, name)
                putExtra(SoilDataCollectionDetailActivity.N, n)
                putExtra(SoilDataCollectionDetailActivity.P, p)
                putExtra(SoilDataCollectionDetailActivity.K, k)
                putExtra(SoilDataCollectionDetailActivity.PH, ph)
                putExtra(SoilDataCollectionDetailActivity.LONGITUDE, long)
                putExtra(SoilDataCollectionDetailActivity.LATITUDE, lat)
                putExtra(SoilDataCollectionDetailActivity.IMAGE, imageUrl)
                putExtra(SoilDataCollectionDetailActivity.DESCRIPTION, desc)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    inner class ViewHolder(binding: SoilDataCollectionLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imgSoilDataCollection: ImageView = binding.imgSoilDataCollection
        val txtNamaVarietasList: TextView = binding.txtNamaVarietasList
        val txtNList: TextView = binding.txtNList
        val txtPList: TextView = binding.txtPList
        val txtKList: TextView = binding.txtKList
        val txtPHList: TextView = binding.txtPHList
    }
}