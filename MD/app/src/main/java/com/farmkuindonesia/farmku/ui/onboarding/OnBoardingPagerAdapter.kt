package com.farmkuindonesia.farmku.ui.onboarding

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.farmkuindonesia.farmku.R

class OnBoardingPagerAdapter(private val context: Context) :PagerAdapter() {

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

    override fun getCount(): Int {
        return titles.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.onboard_layout, container, false)
        val titleTextView: TextView = view.findViewById(R.id.txtTitleOnBoarding)
        val descriptionTextView: TextView = view.findViewById(R.id.txtSubTitleOnBoarding)
        val imageView: ImageView = view.findViewById(R.id.imgOnBoarding)

        titleTextView.setText(titles[position])
        descriptionTextView.setText(descriptions[position])
        imageView.setImageResource(images[position])

        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}

