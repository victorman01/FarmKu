package com.farmkuindonesia.farmku.ui.register

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.farmkuindonesia.farmku.databinding.ActivityRegisterOtpBinding

class RegisterOtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterOtpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.editTextOtp1.jumpToNextEditText(binding.editTextOtp2)
        binding.editTextOtp2.jumpToNextEditText(binding.editTextOtp3, binding.editTextOtp1)
        binding.editTextOtp3.jumpToNextEditText(binding.editTextOtp4, binding.editTextOtp2)
        binding.editTextOtp4.jumpToNextEditText(previousEditText = binding.editTextOtp3)
    }

    fun EditText.jumpToNextEditText(nextEditText: EditText? = null, previousEditText: EditText? = null) {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check if the length of the text is equal to 1
                if (s?.length == 1 && nextEditText != null) {
                    nextEditText.requestFocus() // Set focus to the next EditText
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
                // Check if the length of the text is 0 and there's a previous EditText
                if (s?.length == 0 && previousEditText != null) {
                    previousEditText.requestFocus() // Set focus to the previous EditText
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })
    }

}