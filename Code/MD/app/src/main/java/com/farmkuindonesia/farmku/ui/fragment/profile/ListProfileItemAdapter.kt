package com.farmkuindonesia.farmku.ui.fragment.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.farmkuindonesia.farmku.databinding.ProfileItemLayoutBinding

class ListProfileItemAdapter(private val listProfileItem: ArrayList<ProfileItemData>): RecyclerView.Adapter<ListProfileItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProfileItemLayoutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listProfileItem[position]
        holder.bind(item)
//        val (name, img) = listProfileItem[position]
//        holder.imgIconProfile.setImageResource(img)
//        holder.txtItemProfileName.text = name
    }

    override fun getItemCount(): Int = listProfileItem.size

    inner class ViewHolder(binding: ProfileItemLayoutBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        private val imgIconProfile: ImageView = binding.imgIconProfile
        private val txtItemProfileName: TextView = binding.txtItemProfileName
        fun bind(item: ProfileItemData) {
            imgIconProfile.setImageResource(item.item_icon)
            txtItemProfileName.text = item.item_name
        }
        override fun onClick(view: View) {
            when (absoluteAdapterPosition) {
                0 -> {
                    Toast.makeText(view.context, "Ubah data", Toast.LENGTH_SHORT).show()
                }
                1 -> {
                    Toast.makeText(view.context, "Pengaturan", Toast.LENGTH_SHORT).show()
                }
                2 -> {
                    Toast.makeText(view.context, "Bantuan", Toast.LENGTH_SHORT).show()
                }
                3 -> {
                    Toast.makeText(view.context, "Syarat dan Ketentuan", Toast.LENGTH_SHORT).show()
                }
                4 -> {
                    Toast.makeText(view.context, "Tentang Aplikasi", Toast.LENGTH_SHORT).show()
                }
                5 -> {

                }
            }
//            view.postDelayed({ view.isPressed = false }, 100)
        }
    }
}