package com.farmkuindonesia.farmku.component

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class CustomViewNumEditText : TextInputEditText {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        inputType = android.text.InputType.TYPE_CLASS_NUMBER
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()

                error = if (text.matches(Regex("^[+]?[0-9]{10,}$"))) {
                    if (text.isNotEmpty()) {
                        null
                    } else {
                        "Phone number is required"
                    }
                } else {
                    if (text.isNotEmpty()) {
                        "Invalid input"
                    } else {
                        null
                    }
                }
            }
        })
    }
}
