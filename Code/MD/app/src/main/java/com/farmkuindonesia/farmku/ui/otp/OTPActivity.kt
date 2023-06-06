package com.farmkuindonesia.farmku.ui.otp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.content.res.AppCompatResources
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.databinding.ActivityOtpactivityBinding
import com.farmkuindonesia.farmku.ui.forgotpassword.ResetPasswordActivity
import com.farmkuindonesia.farmku.ui.register.RegisterActivity
import com.farmkuindonesia.farmku.ui.register.RegisterFillDataActivity

class OTPActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOtpactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val fromWhere = intent.getStringExtra(FROMWHERE)
        val isEmail = intent.getBooleanExtra(ISEMAIL,false)

        binding.apply {
            editTextOtp1.apply {
                jumpToNextEditText(binding.editTextOtp2)
                inputType = InputType.TYPE_CLASS_NUMBER
            }
            editTextOtp2.apply {
                jumpToNextEditText(binding.editTextOtp3, binding.editTextOtp1)
                inputType = InputType.TYPE_CLASS_NUMBER
            }
            editTextOtp3.apply {
                jumpToNextEditText(binding.editTextOtp4, binding.editTextOtp2)
                inputType = InputType.TYPE_CLASS_NUMBER
            }
            editTextOtp4.apply {
                jumpToNextEditText(previousEditText = binding.editTextOtp3)
                inputType = InputType.TYPE_CLASS_NUMBER
            }
            btnBackOTP.setOnClickListener{
                finish()
            }

            btnLanjut.setOnClickListener{
                if(fromWhere == "ForgotPasswordActivity"){
                    val intent = Intent(this@OTPActivity,ResetPasswordActivity::class.java)
                    startActivity(intent)
                }else{
                    val phoneNumber = intent.getStringExtra(RegisterActivity.PHONENUMBERREGISTER)
                    val intent = Intent(this@OTPActivity,RegisterFillDataActivity::class.java)
                    intent.putExtra(RegisterActivity.PHONENUMBERREGISTER,phoneNumber)
                    startActivity(intent)
                }
            }
        }

        if(fromWhere == "ForgotPasswordActivity"){
            if(isEmail){
                binding.textViewSubtitleOTP.text = getString(R.string.masukan_otp_email_text)
            }
            else {
                binding.textViewSubtitleOTP.text = getString(R.string.masukan_otp_num_text)
            }
        }else{
            binding.textViewSubtitleOTP.text = getString(R.string.masukan_otp_num_text)
        }
    }

    private fun EditText.jumpToNextEditText(nextEditText: EditText? = null, previousEditText: EditText? = null) {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1 && nextEditText != null) {
                    nextEditText.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })

        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 0 && previousEditText != null) {
                    previousEditText.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })
    }
    companion object{
        const val FROMWHERE = "FROMWHERE"
        const val ISEMAIL = "ISEMAIL"
    }
}