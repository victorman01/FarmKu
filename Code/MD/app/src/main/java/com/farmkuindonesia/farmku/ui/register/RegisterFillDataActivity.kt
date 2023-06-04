package com.farmkuindonesia.farmku.ui.register

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import com.farmkuindonesia.farmku.databinding.ActivityRegisterFillDataBinding

class RegisterFillDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterFillDataBinding
    private lateinit var checkBox: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterFillDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        checkBox = binding.cbSyaratKetentuan
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showTermsAndConditionsDialog()
            }
        }
    }


    private fun showTermsAndConditionsDialog() {

        val builder = AlertDialog.Builder(this)

        builder.setTitle("Syarat dan Ketentuan")
        builder.setMessage("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.")

        builder.setPositiveButton("Terima", DialogInterface.OnClickListener { dialog, _ ->
            dialog.dismiss()
        })

        builder.setNegativeButton("Tolak", DialogInterface.OnClickListener { dialog, _ ->
            checkBox.isChecked = false
            dialog.dismiss()
        })

        builder.setOnCancelListener { checkBox.isChecked = false }

        builder.show()
    }
}