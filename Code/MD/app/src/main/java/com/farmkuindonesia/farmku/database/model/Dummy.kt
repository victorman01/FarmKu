package com.farmkuindonesia.farmku.database.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dummy(
    val name: String,
    val date: String,
    val photo: String
) : Parcelable