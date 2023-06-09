package com.farmkuindonesia.farmku.component

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.farmkuindonesia.farmku.R
import com.google.android.material.textfield.TextInputEditText
import androidx.appcompat.content.res.AppCompatResources

class CustomViewPasswordEditText : TextInputEditText {

    private var isPasswordVisible = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        setupPasswordVisibilityToggle()
        showPassword(false)
    }

    private fun setupPasswordVisibilityToggle() {

        setCompoundDrawablesRelativeWithIntrinsicBounds(
            null,
            null,
            AppCompatResources.getDrawable(context, R.drawable.show_password_off_icon),
            null
        )

        setOnTouchListener { view, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                val drawableEnd = compoundDrawablesRelative[2]
                if (drawableEnd != null && event.rawX >= right - drawableEnd.bounds.width()) {
                    isPasswordVisible = !isPasswordVisible
                    showPassword(isPasswordVisible)
                    view.performClick()
                    return@setOnTouchListener true
                }
            }
            return@setOnTouchListener false
        }
    }

    private fun showPassword(show: Boolean) {
        inputType = if (show) {
            setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                AppCompatResources.getDrawable(context, R.drawable.show_password_on_icon),
                null
            )
            android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
        } else {
            setCompoundDrawablesRelativeWithIntrinsicBounds(
                null,
                null,
                AppCompatResources.getDrawable(context, R.drawable.show_password_off_icon),
                null
            )
            android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
        }
        hint = if (show) {
            context.getString(R.string.showPassword)
        } else {
            context.getString(R.string.kata_sandi_anda)
        }
        setSelection(text?.length ?: 0)
    }
}
