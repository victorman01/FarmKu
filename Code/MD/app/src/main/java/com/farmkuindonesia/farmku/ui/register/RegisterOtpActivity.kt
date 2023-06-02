package com.farmkuindonesia.farmku.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmkuindonesia.farmku.databinding.ActivityRegisterOtpBinding

class RegisterOtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}