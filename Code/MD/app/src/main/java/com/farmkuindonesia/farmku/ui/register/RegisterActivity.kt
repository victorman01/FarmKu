package com.farmkuindonesia.farmku.ui.register

import android.app.AlertDialog
import android.content.DialogInterface
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
            val phoneNumber = binding.txtNumberRegister.text.toString()
            val intent = Intent(this@RegisterActivity, OTPActivity::class.java)
            intent.putExtra(OTPActivity.FROMWHERE , "RegisterActivity")
            intent.putExtra(PHONENUMBERREGISTER, phoneNumber)
            startActivity(intent)
        }
    }

    companion object{
        const val PHONENUMBERREGISTER = "PHONENUMBERREGISTER"
    }
}