package com.farmkuindonesia.farmku.ui.register

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
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

    private lateinit var namesProvince: MutableList<String>
    private lateinit var idsProvince: MutableList<String>
    private lateinit var namesDistrict: MutableList<String>
    private lateinit var idsDistrict: MutableList<String>
    private lateinit var namesRegency: MutableList<String>
    private lateinit var idsRegency: MutableList<String>
    private lateinit var namesVillage: MutableList<String>
    private lateinit var idsVillage: MutableList<String>

    val provinceHint = "Pilih provinsi anda"
    val districtHint = "Pilih kabupaten/kota anda"
    val regencyHint = "Pilih kecamatan anda"
    val villageHint = "Pilih desa/kelurahan anda"

    var selectedIdp = "0"
    var selectedIdd = "0"
    var selectedIdr = "0"
    var selectedIdv = "0"

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

        binding.apply{
            spinnerRegency.isEnabled = false
            spinnerDistrict.isEnabled = false
            spinnerVillage.isEnabled = false
        }

        binding.spinnerProvince.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    namesDistrict = mutableListOf()
                    namesDistrict.add(districtHint)
                    idsDistrict = mutableListOf()
                    idsDistrict.add("0")

                    if (binding.spinnerProvince.selectedItemPosition != 0) {
                        selectedIdp = idsProvince[position]
                        getDistrict(selectedIdp)
                    } else{
                        selectedIdp = "0"
                    }
                    binding.spinnerDistrict.setSelection(0)

                    if (selectedIdp != "0"){
                        binding.spinnerDistrict.isEnabled = true
                        binding.spinnerRegency.isEnabled = false
                        binding.spinnerVillage.isEnabled = false
                    }
                    else{
                        binding.spinnerDistrict.isEnabled = false
                        binding.spinnerRegency.isEnabled = false
                        binding.spinnerVillage.isEnabled = false
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do Nothing
                }
            }

        binding.spinnerDistrict.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    namesRegency = mutableListOf()
                    namesRegency.add(regencyHint)
                    idsRegency = mutableListOf()
                    idsRegency.add("0")

                    if (binding.spinnerDistrict.selectedItemPosition != 0) {
                        selectedIdd = idsDistrict[position]
                        getRegency(selectedIdd)
                    } else{
                        selectedIdd = "0"
                    }
                    binding.spinnerRegency.setSelection(0)

                    if (selectedIdd != "0"){
                        binding.spinnerRegency.isEnabled = true
                        binding.spinnerVillage.isEnabled = false
                    }
                    else{
                        binding.spinnerRegency.isEnabled = false
                        binding.spinnerVillage.isEnabled = false
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do Nothing
                }
            }

        binding.spinnerRegency.onItemSelectedListener =
            object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    namesVillage = mutableListOf()
                    namesVillage.add(villageHint)
                    idsVillage = mutableListOf()
                    idsVillage.add("0")

                    binding.spinnerVillage.setSelection(0)
                    if (binding.spinnerDistrict.selectedItemPosition != 0) {
                        selectedIdr = idsRegency[position]
                        getVillage(selectedIdr)
                    } else{
                        selectedIdr = "0"
                    }
                    binding.spinnerVillage.isEnabled = selectedIdr != "0"

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do Nothing
                }
            }

        binding.spinnerVillage.onItemSelectedListener =
            object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (binding.spinnerDistrict.selectedItemPosition != 0) {
                        selectedIdv = idsVillage[position]
                    }
                    addressSelected = selectedIdv
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>?
                ) {
                    // Do Nothing
                }
            }

        setInitialSpinner(binding.spinnerProvince, provinceHint)
        setInitialSpinner(binding.spinnerDistrict, districtHint)
        setInitialSpinner(binding.spinnerRegency, regencyHint)
        setInitialSpinner(binding.spinnerVillage, villageHint)

        binding.spinnerProvince.setSelection(0)
        registerViewModel.getProvince().observe(this) { provinceList ->

            namesProvince = mutableListOf()
            namesProvince.add(provinceHint)

            idsProvince = mutableListOf()
            idsProvince.add("0")

            provinceList?.forEach {
                namesProvince.add(it?.name.toString())
                idsProvince.add(it?.id.toString())
            }

            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                namesProvince
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerProvince.adapter = adapter
        }


        binding.btnRegisterFillData.setOnClickListener {
            val phoneNumber = intent.getStringExtra(RegisterActivity.PHONENUMBERREGISTER)
            val name = binding.txtNameFillData.text.toString()
            val email = binding.txtEmailFillData.text.toString()
            val password = binding.txtKataSandiFillData.text.toString()
            val repPass = binding.txtUlangKataSandiFillData.text.toString()

            Toast.makeText(this, addressSelected, Toast.LENGTH_SHORT).show()
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
                    Toast.makeText(this, getString(R.string.password_tidak_sama_text), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.mohon_isi_seluruh_data), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getDistrict(selectedIdp: String){
        registerViewModel.getDistrict(selectedIdp)
            .observe(this@RegisterFillDataActivity) { districtList ->
                districtList?.forEach {
                    namesDistrict.add(it?.name.toString())
                    idsDistrict.add(it?.id.toString())
                }

                val districtAdapter = ArrayAdapter(
                    this@RegisterFillDataActivity,
                    android.R.layout.simple_spinner_item,
                    namesDistrict
                )
                districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerDistrict.adapter = districtAdapter

            }
    }

    private fun getRegency(selectedIdd: String){
        registerViewModel.getRegency(selectedIdd)
            .observe(this@RegisterFillDataActivity) { regencyList ->
                regencyList?.forEach {
                    namesRegency.add(it?.name.toString())
                    idsRegency.add(it?.id.toString())
                }

                val regencyAdapter = ArrayAdapter(
                    this@RegisterFillDataActivity,
                    android.R.layout.simple_spinner_item,
                    namesRegency
                )
                regencyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerRegency.adapter = regencyAdapter

            }
    }

    private fun getVillage(selectedIdr: String){
        registerViewModel.getVillage(selectedIdr)
            .observe(this@RegisterFillDataActivity) { villageList ->

                villageList?.forEach {
                    namesVillage.add(it?.name.toString())
                    idsVillage.add(it?.id.toString())
                }

                val villageAdapter =
                    ArrayAdapter(
                        this@RegisterFillDataActivity,
                        android.R.layout.simple_spinner_item,
                        namesVillage
                    )
                villageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerVillage.adapter = villageAdapter
            }
    }

    private fun setInitialSpinner(spinner: Spinner, hint: String){
        val hints = mutableListOf(hint)
        val dadapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            hints
        )
        dadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = dadapter
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