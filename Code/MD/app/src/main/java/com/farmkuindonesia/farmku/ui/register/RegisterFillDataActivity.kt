package com.farmkuindonesia.farmku.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmkuindonesia.farmku.databinding.ActivityRegisterFillDataBinding

class RegisterFillDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterFillDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterFillDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
    }
}