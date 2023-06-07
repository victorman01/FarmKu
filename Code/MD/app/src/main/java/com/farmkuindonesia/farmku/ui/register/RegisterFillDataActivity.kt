package com.farmkuindonesia.farmku.ui.register

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.databinding.ActivityRegisterFillDataBinding
import com.farmkuindonesia.farmku.ui.ViewModelFactory
import com.farmkuindonesia.farmku.ui.login.LoginActivity

class RegisterFillDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterFillDataBinding
    private lateinit var checkBox: CheckBox
    private lateinit var viewModelFac: ViewModelFactory
    private lateinit var registerViewModel: RegisterViewModel

    private var addressSelected: String = ""

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

        val provinceHint = "Pilih provinsi anda"
        val districtHint = "Pilih kabupaten/kota anda"
        val regencyHint = "Pilih kecamatan anda"
        val villageHint = "Pilih desa/kelurahan anda"

        registerViewModel.getProvince().observe(this) { provinceList ->
            val namesProvince: MutableList<String> = mutableListOf()
            val idsProvince: MutableList<String> = mutableListOf()
            namesProvince.add(provinceHint)
            idsProvince.add("")

            provinceList?.forEach {
                val idp = it?.id.toString()
                val namep = it?.name.toString()
                namesProvince.add(namep)
                idsProvince.add(idp)
            }

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                namesProvince
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerProvince.adapter = adapter

            binding.spinnerProvince.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        val selectedIdp = idsProvince[position]
                        val namesDistrict: MutableList<String> = mutableListOf()
                        val idsDistrict: MutableList<String> = mutableListOf()

                        namesDistrict.add(districtHint)
                        idsDistrict.add("")

                        registerViewModel.getDistrict(selectedIdp)
                            .observe(this@RegisterFillDataActivity) { districtList ->
                                districtList?.forEach {
                                    val idd = it?.id.toString()
                                    val named = it?.name.toString()
                                    namesDistrict.add(named)
                                    idsDistrict.add(idd)
                                }

                                val districtAdapter = ArrayAdapter(
                                    this@RegisterFillDataActivity,
                                    android.R.layout.simple_spinner_item,
                                    namesDistrict
                                )
                                districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                binding.spinnerDistrict.adapter = districtAdapter
                                binding.spinnerDistrict.onItemSelectedListener =
                                    object : AdapterView.OnItemSelectedListener {
                                        override fun onItemSelected(
                                            parent: AdapterView<*>?,
                                            view: View?,
                                            position: Int,
                                            id: Long
                                        ) {
                                            val namesRegency: MutableList<String> = mutableListOf()
                                            val idsRegency: MutableList<String> = mutableListOf()

                                            val selectedIdd = idsDistrict[position]
                                            namesRegency.add(regencyHint)
                                            idsRegency.add("")
                                            registerViewModel.getRegency(selectedIdd)
                                                .observe(this@RegisterFillDataActivity) { regencyList ->

                                                    regencyList?.forEach {
                                                        val idr = it?.id.toString()
                                                        val namer = it?.name.toString()
                                                        namesRegency.add(namer)
                                                        idsRegency.add(idr)
                                                    }

                                                    val regencyAdapter = ArrayAdapter(
                                                        this@RegisterFillDataActivity,
                                                        android.R.layout.simple_spinner_item,
                                                        namesRegency
                                                    )
                                                    regencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                                                    binding.spinnerRegency.adapter = regencyAdapter
                                                    binding.spinnerRegency.onItemSelectedListener =
                                                        object :
                                                            AdapterView.OnItemSelectedListener {
                                                            override fun onItemSelected(
                                                                parent: AdapterView<*>?,
                                                                view: View?,
                                                                position: Int,
                                                                id: Long
                                                            ) {
                                                                val namesVillage: MutableList<String> =
                                                                    mutableListOf()
                                                                val idsVillage: MutableList<String> =
                                                                    mutableListOf()
                                                                val selectedIdr =
                                                                    idsRegency[position]
                                                                namesVillage.add(villageHint)
                                                                idsVillage.add("")
                                                                registerViewModel.getVillage(
                                                                    selectedIdr
                                                                )
                                                                    .observe(this@RegisterFillDataActivity) { villageList ->

                                                                        villageList?.forEach {
                                                                            val idv =
                                                                                it?.id.toString()
                                                                            val namev =
                                                                                it?.name.toString()
                                                                            namesVillage.add(namev)
                                                                            idsVillage.add(idv)
                                                                        }

                                                                        val villageAdapter =
                                                                            ArrayAdapter(
                                                                                this@RegisterFillDataActivity,
                                                                                android.R.layout.simple_spinner_item,
                                                                                namesVillage
                                                                            )
                                                                        villageAdapter.setDropDownViewResource(
                                                                            android.R.layout.simple_spinner_dropdown_item
                                                                        )
                                                                        binding.spinnerVillage.adapter =
                                                                            villageAdapter
                                                                        binding.spinnerVillage.onItemSelectedListener =
                                                                            object :
                                                                                AdapterView.OnItemSelectedListener {
                                                                                override fun onItemSelected(
                                                                                    parent: AdapterView<*>?,
                                                                                    view: View?,
                                                                                    position: Int,
                                                                                    id: Long
                                                                                ) {
                                                                                    val selectedIdv =
                                                                                        idsVillage[position]
                                                                                    addressSelected =
                                                                                        selectedIdv
                                                                                }

                                                                                override fun onNothingSelected(
                                                                                    parent: AdapterView<*>?
                                                                                ) {
                                                                                    // Do Nothing
                                                                                }
                                                                            }
                                                                    }
                                                            }

                                                            override fun onNothingSelected(parent: AdapterView<*>?) {
                                                                // Do Nothing
                                                            }
                                                        }
                                                }
                                        }

                                        override fun onNothingSelected(parent: AdapterView<*>?) {
                                            // Do Nothing
                                        }
                                    }
                            }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        // Do Nothing
                    }
                }
        }


        binding.btnRegisterFillData.setOnClickListener {
            val phoneNumber = intent.getStringExtra(RegisterActivity.PHONENUMBERREGISTER)
            val name = binding.txtNameFillData.text.toString()
            val email = binding.txtEmailFillData.text.toString()
            val password = binding.txtKataSandiFillData.text.toString()
            val repPass = binding.txtUlangKataSandiFillData.text.toString()

            Toast.makeText(
                this,
                addressSelected,
                Toast.LENGTH_SHORT
            )
                .show()
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && repPass.isNotEmpty()) {
                if (password == repPass) {
                    registerViewModel.register(
                        name,
                        email,
                        addressSelected,
                        phoneNumber.toString(),
                        password
                    ).observe(this) {
                        if (it.success == true) {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.password_tidak_sama_text),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.mohon_isi_seluruh_data),
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    private fun showTermsAndConditionsDialog() {

        val builder = AlertDialog.Builder(this)

        builder.setTitle(getString(R.string.syarat_dan_ketentuan_text))
        builder.setMessage(getString(R.string.isi_syarat_dan_ketentuan_text))

        builder.setPositiveButton(getString(R.string.terima_text)) { dialog, _ ->
            dialog.dismiss()
        }

        builder.setNegativeButton(getString(R.string.tolak_text)) { dialog, _ ->
            checkBox.isChecked = false
            dialog.dismiss()
        }

        builder.setOnCancelListener { checkBox.isChecked = false }

        builder.show()
    }
}