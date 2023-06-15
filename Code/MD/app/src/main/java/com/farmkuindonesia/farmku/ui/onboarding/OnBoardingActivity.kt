package com.farmkuindonesia.farmku.ui.onboarding

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.databinding.ActivityOnBoardingBinding
import com.farmkuindonesia.farmku.ui.login.LoginActivity

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: OnBoardingPagerAdapter
    private lateinit var dotsLayout: LinearLayout
    private lateinit var dot: Array<ImageView?>
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
        val activeDot: Drawable? = ContextCompat.getDrawable(this, R.drawable.active_dot)
        val inactiveDot: Drawable? = ContextCompat.getDrawable(this, R.drawable.inactive_dot)

        for (i in dot.indices) {
            dot[i]?.setImageDrawable(inactiveDot)
        }
        dot[position]?.setImageDrawable(activeDot)
    }

    private fun addDots() {
        dot = Array(adapter.count) { null }
        val activeDot: Drawable? = ContextCompat.getDrawable(this, R.drawable.active_dot)
        val inactiveDot: Drawable? = ContextCompat.getDrawable(this, R.drawable.inactive_dot)

        for (i in dot.indices) {
            dot[i] = ImageView(this)
            dot[i]?.setImageDrawable(inactiveDot)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            dotsLayout.addView(dot[i], params)
        }
        dot[0]?.setImageDrawable(activeDot)
    }
}
