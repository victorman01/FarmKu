package com.farmkuindonesia.farmku.ui.register

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.databinding.ActivityRegisterFillDataBinding
import com.farmkuindonesia.farmku.ui.ViewModelFactory
import com.farmkuindonesia.farmku.ui.login.LoginActivity
import com.farmkuindonesia.farmku.ui.login.LoginViewModel

class RegisterFillDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterFillDataBinding
    private lateinit var checkBox: CheckBox
    private lateinit var viewModelFac: ViewModelFactory
    private lateinit var registerViewModel: RegisterViewModel

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

        viewModelFac = ViewModelFactory.getInstance(this)
        registerViewModel = ViewModelProvider(this, viewModelFac)[RegisterViewModel::class.java]

        binding.btnRegisterFillData.setOnClickListener{
            val phoneNumber = intent.getStringExtra("Data")
            val name = binding.txtNameFillData.text.toString()
            val email = binding.txtEmailFillData.text.toString()
            val address = binding.txtAlamatFillData.text.toString()
            val password = binding.txtKataSandiFillData.text.toString()
            val repPass = binding.txtUlangKataSandiFillData.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && address.isNotEmpty() && password.isNotEmpty() && repPass.isNotEmpty()){
                if (password == repPass){
                    registerViewModel.register(name, email, address, phoneNumber.toString(), password).observe(this){
                        if(it.success == true){
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                }
                else{
                    Toast.makeText(this,"Password yang anda isi tidak sama", Toast.LENGTH_SHORT).show()
                }
            }
            else{
                Toast.makeText(this,getString(R.string.mohon_isi_seluruh_data), Toast.LENGTH_SHORT).show()
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