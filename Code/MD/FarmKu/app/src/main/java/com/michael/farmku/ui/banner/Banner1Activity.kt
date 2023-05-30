package com.michael.farmku.ui.banner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.michael.farmku.databinding.ActivityBanner1Binding
import com.michael.farmku.ui.login.LoginActivity
import com.michael.farmku.ui.splashScreen.MainActivity

class Banner1Activity : AppCompatActivity() {

    private var _binding: ActivityBanner1Binding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityBanner1Binding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnNextBanner1.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnSkipBanner1.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}