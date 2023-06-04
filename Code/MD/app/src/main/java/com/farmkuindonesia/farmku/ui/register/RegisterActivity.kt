package com.farmkuindonesia.farmku.ui.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmkuindonesia.farmku.databinding.ActivityRegisterBinding
import com.farmkuindonesia.farmku.ui.otp.OTPActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.btnLoginRegister.setOnClickListener{
            finish()
        }

        binding.btnRegisterRegister.setOnClickListener {
            val intent = Intent(this@RegisterActivity, OTPActivity::class.java)
            intent.putExtra(OTPActivity.FROMWHERE , "RegisterActivity")
            startActivity(intent)
            finish()
        }
    }
}