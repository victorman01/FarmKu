package com.farmkuindonesia.farmku.ui.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.farmkuindonesia.farmku.R

class OnBoardingPagerAdapter(private val context: Context) :
    RecyclerView.Adapter<OnBoardingPagerAdapter.ViewHolder>() {

    private val titles = arrayOf(
        R.string.onboarding1_title,
        R.string.onboarding2_title,
        R.string.onboarding3_title
    )
    private val descriptions = arrayOf(
        R.string.onboarding1_subtitle,
        R.string.onboarding2_subtitle,
        R.string.onboarding3_subtitle
    )
    private val images = arrayOf(
        R.drawable.onboarding1,
        R.drawable.onboarding2,
        R.drawable.onboarding3
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.onboard_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return titles.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.txtTitleOnBoarding)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.txtSubTitleOnBoarding)
        private val imageView: ImageView = itemView.findViewById(R.id.imgOnBoarding)

        fun bind(position: Int) {
            titleTextView.setText(titles[position])
            descriptionTextView.setText(descriptions[position])
            imageView.setImageResource(images[position])
        }
    }
}

