package com.farmkuindonesia.farmku.ui.banner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmkuindonesia.farmku.databinding.ActivityBannerBinding
import com.farmkuindonesia.farmku.ui.login.LoginActivity

class BannerActivity : AppCompatActivity() {
    private var _binding: ActivityBannerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBannerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnNextBanner.setOnClickListener{

        }

        binding.btnSkipBanner.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}