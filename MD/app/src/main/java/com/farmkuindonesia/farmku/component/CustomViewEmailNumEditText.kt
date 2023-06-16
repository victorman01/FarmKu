package com.farmkuindonesia.farmku.component

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class CustomViewEmailNumEditText : TextInputEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()

                if (android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                    error = if (text.isNotEmpty()) {
                        null
                    } else {
                        "Email is required"
                    }
                } else if (text.matches(Regex("^[+]?[0-9]{10,13}$"))) {
                    error = if (text.isNotEmpty()) {
                        null
                    } else {
                        "Phone number is required"
                    }
                } else {
                    error = if (text.isNotEmpty()) {
                        "Invalid input"
                    } else {
                        null
                    }
                }
            }
        })
    }

}
