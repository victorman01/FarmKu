package com.farmkuindonesia.farmku.ui.fragment.home.hotnews

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dummy(
    val name: String,
    val date: String,
    val photo: Int
) : Parcelable