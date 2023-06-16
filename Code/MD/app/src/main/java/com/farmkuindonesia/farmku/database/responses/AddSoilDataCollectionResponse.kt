package com.farmkuindonesia.farmku.database.responses

import com.google.gson.annotations.SerializedName

data class AddSoilDataCollectionResponseItem(

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("nama_varietas")
    val namaVarietas: String? = null,

    @field:SerializedName("N")
    val n: Double? = null,

    @field:SerializedName("P")
    val p: Double? = null,

    @field:SerializedName("K")
    val k: Double? = null,

    @field:SerializedName("PH")
    val pH: Double? = null,

    @field:SerializedName("Description")
    val description: String? = null,

    @field:SerializedName("Latitude")
    val latitude: Double? = null,

    @field:SerializedName("Longitude")
    val longitude: Double? = null,

    @field:SerializedName("Image")
    val image: List<ImageItem>? = null,
)

data class AddImageItem(

    @field:SerializedName("path")
    val path: String? = null,

    @field:SerializedName("size")
    val size: Int? = null,

    @field:SerializedName("encoding")
    val encoding: String? = null,

    @field:SerializedName("filename")
    val filename: String? = null,

    @field:SerializedName("mimetype")
    val mimetype: String? = null,

    @field:SerializedName("fieldname")
    val fieldname: String? = null,

    @field:SerializedName("destination")
    val destination: String? = null,

    @field:SerializedName("originalname")
    val originalname: String? = null
)
