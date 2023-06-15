package com.farmkuindonesia.farmku.database.responses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

@Parcelize
data class SoilDataCollectionResponse(

	@field:SerializedName("SoilDataCollectionResponse")
	val soilDataCollectionResponse: List<SoilDataCollectionResponseItem?>? = null
) : Parcelable

@Parcelize
data class SoilDataCollectionResponseItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("N")
	val n: Double? = null,

	@field:SerializedName("P")
	val p: Double? = null,

	@field:SerializedName("K")
	val k: Double? = null,

	@field:SerializedName("PH")
	val pH: Double? = null,

	@field:SerializedName("Longitude")
	val longitude: Double? = null,

	@field:SerializedName("Latitude")
	val latitude: Double? = null,

	@field:SerializedName("Image")
	val image: Image? = null,

	@field:SerializedName("Description")
	val description: String? = null
) : Parcelable

@Parcelize
data class Image(

	@field:SerializedName("data")
	val data: ByteArray? = null,

	@field:SerializedName("type")
	val type: String? = null
) : Parcelable