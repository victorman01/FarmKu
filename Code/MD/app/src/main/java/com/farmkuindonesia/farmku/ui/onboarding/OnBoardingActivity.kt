package com.farmkuindonesia.farmku.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.databinding.ActivityOnBoardingBinding
import com.farmkuindonesia.farmku.ui.login.LoginActivity

class OnBoardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnBoardingBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: OnBoardingPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewPager = binding.viewPagerOnBoarding
        adapter = OnBoardingPagerAdapter(this)
        viewPager.adapter = adapter

        binding.btnNextBoarding.setOnClickListener {
            if (viewPager.currentItem < adapter.itemCount - 1) {
                viewPager.currentItem = viewPager.currentItem + 1
                if(viewPager.currentItem == adapter.itemCount - 1){
                    binding.btnNextBoarding.text = getString(R.string.memulai_aplikasi_text)
                    binding.btnSkipBoarding.visibility = View.GONE
                }
            } else{
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
