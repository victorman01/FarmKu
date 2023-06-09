package com.farmkuindonesia.farmku.ui.fragment.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.recyclerview.widget.RecyclerView
import com.farmkuindonesia.farmku.database.Preferences
import com.farmkuindonesia.farmku.databinding.ProfileItemLayoutBinding
import com.farmkuindonesia.farmku.ui.ViewModelFactory
import com.farmkuindonesia.farmku.ui.main.MainActivity
import com.farmkuindonesia.farmku.ui.onboarding.OnBoardingActivity
import com.google.firebase.auth.FirebaseAuth

class ListProfileItemAdapter(
    private val context: Context,
    private val listProfileItem: ArrayList<ProfileItemData>,
    private val callback: MainActivityCallback
) : RecyclerView.Adapter<ListProfileItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProfileItemLayoutBinding.inflate(LayoutInflater.from(context), viewGroup, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listProfileItem[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = listProfileItem.size

    inner class ViewHolder(binding: ProfileItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

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
                    signOut(view)
                }
            }
        }

        private lateinit var auth: FirebaseAuth

        private fun signOut(view: View) {
            val preferences = context.getSharedPreferences(Preferences.PREFERENCES, Context.MODE_PRIVATE)
            when (preferences.getString(Preferences.LOGGEDINWITH, "NONE")) {
                "EMAIL" -> {
                    Preferences.setLogout(preferences)
                }
                "GOOGLE" -> {
                    auth.signOut()
                }
                else -> {
                    Toast.makeText(view.context, "No account is logged in", Toast.LENGTH_SHORT).show()
                }
            }
            view.context.startActivity(Intent(view.context, OnBoardingActivity::class.java))
            callback.finishMainActivity()
        }
    }

    interface MainActivityCallback {
        fun finishMainActivity()
    }
}
