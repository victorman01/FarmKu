package com.farmkuindonesia.farmku.component

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class CustomViewEmailEditText : TextInputEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var isEmail = false

    private fun checkEmailValidity(email: String) {
        isEmail = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        error = if (email.isNotEmpty() && !isEmail) {
            "Invalid email format"
        } else {
            null
        }
    }

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                checkEmailValidity(text)
            }
        })
    }
}
