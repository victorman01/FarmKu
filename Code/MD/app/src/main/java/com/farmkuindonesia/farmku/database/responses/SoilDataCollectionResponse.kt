package com.farmkuindonesia.farmku.database.responses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class SoilDataCollectionResponse(

	@field:SerializedName("SoilDataCollectionResponse")
	val soilDataCollectionResponse: List<SoilDataCollectionResponseItem?>? = null
) : Parcelable

@Parcelize
data class SoilDataCollectionResponseItem(

	@field:SerializedName("P")
	val p: Double? = null,

	@field:SerializedName("Description")
	val description: String? = null,

	@field:SerializedName("nama_varietas")
	val namaVarietas: String? = null,

	@field:SerializedName("PH")
	val pH: Double? = null,

	@field:SerializedName("Latitude")
	val latitude: Double? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("K")
	val k: Double? = null,

	@field:SerializedName("Image")
	val image: List<ImageItem?>? = null,

	@field:SerializedName("Longitude")
	val longitude: Double? = null,

	@field:SerializedName("N")
	val n: Double? = null
) : Parcelable

@Parcelize
data class ImageItem(

	@field:SerializedName("fieldname")
	val fieldname: String? = null,

	@field:SerializedName("path")
	val path: String? = null,

	@field:SerializedName("filename")
	val filename: String? = null,

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("originalname")
	val originalname: String? = null,

	@field:SerializedName("destination")
	val destination: String? = null,

	@field:SerializedName("mimetype")
	val mimetype: String? = null,

	@field:SerializedName("encoding")
	val encoding: String? = null
) : Parcelable
