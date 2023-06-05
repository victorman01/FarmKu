package com.farmkuindonesia.farmku.database.responses

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SignUpResponse(

    @field:SerializedName("success")
    val success: Boolean? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("message")
    val message: String? = null
) : Parcelable