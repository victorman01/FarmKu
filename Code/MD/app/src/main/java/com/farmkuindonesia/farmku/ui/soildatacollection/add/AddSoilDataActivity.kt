package com.farmkuindonesia.farmku.ui.soildatacollection.add

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.farmkuindonesia.farmku.databinding.ActivityAddSoilDataBinding

class AddSoilDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddSoilDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSoilDataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Tambahkan data tanah"
        
        binding.txtDescriptionAddSoilData.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Do nothing
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length <= 200) {
                    binding.txtCharCounterAddSoil.text = "${s.length}/200"
                    if (s.length == 200){
                        Toast.makeText(this@AddSoilDataActivity, "Maksimum karakter tercapai", Toast.LENGTH_SHORT).show()
                        binding.txtDescriptionAddSoilData.clearFocus()
                        hideKeyboard(this@AddSoilDataActivity)
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Do nothing
            }
        })
    }

    fun hideKeyboard(context: Context) {
        val view: View? = this.currentFocus
        val inputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}