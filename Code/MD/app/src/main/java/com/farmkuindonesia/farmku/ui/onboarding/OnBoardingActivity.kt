package com.farmkuindonesia.farmku.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.viewpager.widget.ViewPager
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.databinding.ActivityOnBoardingBinding
import com.farmkuindonesia.farmku.ui.login.LoginActivity

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: OnBoardingPagerAdapter
    private lateinit var dotsLayout: LinearLayout
    private lateinit var dot: Array<TextView?>
    private var currentPage = 0
    private var isLastPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        dotsLayout = binding.layoutDots

        viewPager = binding.viewPagerOnBoarding
        adapter = OnBoardingPagerAdapter(this)
        viewPager.adapter = adapter
        addDots()

        binding.btnGetStarted.visibility = View.GONE
        binding.btnNext.setOnClickListener {
            scrollToNextPage()
        }

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                // Not needed for updating dots
            }

            override fun onPageSelected(position: Int) {
                updateDots(position)
                currentPage = position

                if (position == adapter.count - 1) {
                    isLastPage = true
                    fadeOutAnimation(binding.btnNext)
                    fadeInAnimation(binding.btnGetStarted)
                } else {
                    if(isLastPage){
                        fadeOutAnimation(binding.btnGetStarted)
                        fadeInAnimation(binding.btnNext)
                        isLastPage = false
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                // Not needed for updating dots
            }
        })

        binding.btnGetStarted.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun updateDots(position: Int) {
        val grayColor = AppCompatResources.getColorStateList(this, R.color.gray)
        val blackColor = AppCompatResources.getColorStateList(this, R.color.black)

        for (i in dot.indices) {
            dot[i]?.setTextColor(grayColor)
        }
        dot[position]?.setTextColor(blackColor)
    }

    private fun scrollToNextPage() {
        if (currentPage < adapter.count - 1) {
            viewPager.setCurrentItem(currentPage + 1, true)
        }
    }

    private fun addDots() {
        dot = Array(adapter.count) { null }
        val grayColor = AppCompatResources.getColorStateList(this, R.color.gray)
        val blackColor = AppCompatResources.getColorStateList(this, R.color.black)

        for (i in dot.indices) {
            dot[i] = TextView(this)
            dot[i]?.text = "•"
            dot[i]?.textSize = 35f
            dot[i]?.setTextColor(grayColor)

            dotsLayout.addView(dot[i])
        }
        dot[0]?.setTextColor(blackColor)
    }

    private fun fadeInAnimation(view: View) {
        view.visibility = View.VISIBLE
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 500
        fadeIn.fillAfter = true
        fadeIn.interpolator = DecelerateInterpolator()
        view.startAnimation(fadeIn)
    }

    private fun fadeOutAnimation(view: View) {
        view.visibility = View.GONE
        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.duration = 500
        fadeOut.fillAfter = true
        fadeOut.interpolator = DecelerateInterpolator()
        view.startAnimation(fadeOut)
    }

    override fun onSupportNavigateUp(): Boolean {
        if (currentPage == adapter.count - 1) {
            isLastPage = true
            binding.btnGetStarted.visibility = View.VISIBLE
            fadeOutAnimation(binding.btnNext)
            return true
        }
        return super.onSupportNavigateUp()
    }
}