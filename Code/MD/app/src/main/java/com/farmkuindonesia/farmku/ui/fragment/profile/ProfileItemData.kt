package com.farmkuindonesia.farmku.ui.fragment.profile

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProfileItemData(
    val item_name: String,
    val item_icon: Int
) : Parcelable