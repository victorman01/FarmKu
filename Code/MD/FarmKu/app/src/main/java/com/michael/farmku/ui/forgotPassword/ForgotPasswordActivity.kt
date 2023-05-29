package com.michael.farmku.ui.forgotPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.michael.farmku.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private var _binding: ActivityForgotPasswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnBackForgot.setOnClickListener{
            finish()
        }
    }
}