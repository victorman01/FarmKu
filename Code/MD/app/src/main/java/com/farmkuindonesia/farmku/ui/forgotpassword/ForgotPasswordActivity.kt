package com.farmkuindonesia.farmku.ui.forgotpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.databinding.ActivityForgotPasswordBinding
import com.farmkuindonesia.farmku.ui.otp.OTPActivity

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private var isEmail = true;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.apply {
            btnBackForgot.setOnClickListener {
                finish()
            }

            btnfpEmail.setOnClickListener {
                isEmail = true
                txtSubtitleForgot.text = getString(R.string.forgot_subtitle_email)
                txtDataUser.text = getString(R.string.email_text)
                btnfpEmail.background =
                    AppCompatResources.getDrawable(this@ForgotPasswordActivity, R.color.light_green)
                btnfpNum.background = null
                txtIsiEmail.visibility = View.VISIBLE
                txtIsiNumber.visibility = View.GONE
            }
            btnfpNum.setOnClickListener {
                isEmail = false
                txtSubtitleForgot.text = getString(R.string.forgot_subtitle_hp)
                txtDataUser.text = getString(R.string.nomor_hp_text)
                btnfpEmail.background = null
                btnfpNum.background =
                    AppCompatResources.getDrawable(this@ForgotPasswordActivity, R.color.light_green)
                txtIsiEmail.visibility = View.GONE
                txtIsiNumber.visibility = View.VISIBLE
            }

            btnSendForgot.setOnClickListener {
                val intent = Intent(this@ForgotPasswordActivity, OTPActivity::class.java)
                intent.putExtra(OTPActivity.FROMWHERE, "ForgotPasswordActivity")
                intent.putExtra(PHONENUMBERFORGOT, txtIsiNumber.text.toString())
                intent.putExtra(OTPActivity.ISEMAIL, isEmail)
                startActivity(intent)
            }
        }
    }


    companion object{
        const val PHONENUMBERFORGOT = "PHONENUMBERFORGOT"
    }
}