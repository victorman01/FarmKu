package com.farmkuindonesia.farmku.ui.fragment.listland

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.farmkuindonesia.farmku.database.responses.LandItem
import com.farmkuindonesia.farmku.databinding.DaftarLahanLayoutBinding
import com.farmkuindonesia.farmku.ui.fragment.listland.land.LandActivity

class ListLandAdapter(private val listLand: List<LandItem?>) :
    RecyclerView.Adapter<ListLandAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            DaftarLahanLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listLand.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val land = listLand[position]
        val address =
            listLand[position]?.address?.village + ", " + listLand[position]?.address?.regency + ", " + listLand[position]?.address?.district + ", " + listLand[position]?.address?.province
        with(holder.binding) {
            txtLocLand.text = land?.name
            txtDateLand.text = address
            root.setOnClickListener{
                val intent = Intent(it.context, LandActivity::class.java)
                intent.putExtra(LandActivity.IDLAND,land?.id)
                intent.putExtra(LandActivity.NAMELAND,land?.name)
                it.context.startActivity(intent)
            }
        }
    }

    inner class ViewHolder(val binding: DaftarLahanLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}
