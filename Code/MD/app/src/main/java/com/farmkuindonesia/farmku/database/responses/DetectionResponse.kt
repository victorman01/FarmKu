package com.farmkuindonesia.farmku.database.responses

import com.google.gson.annotations.SerializedName

data class DetectionResponse(
    @field:SerializedName("confidence")
    val confidence: String? = null,

    @field:SerializedName("result")
    val result: String? = null
)
