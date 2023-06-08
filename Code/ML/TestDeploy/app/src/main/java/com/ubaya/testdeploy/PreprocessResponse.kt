package com.ubaya.testdeploy

import com.google.gson.annotations.SerializedName

data class PreprocessResponse(
    @field:SerializedName("confidence")
    val confidence: String? = null,

    @field:SerializedName("result")
    val result: String? = null
)
