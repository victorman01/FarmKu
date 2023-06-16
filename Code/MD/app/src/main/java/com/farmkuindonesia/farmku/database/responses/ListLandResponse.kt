package com.farmkuindonesia.farmku.database.responses

import com.google.gson.annotations.SerializedName

data class ListLandResponse(

	@field:SerializedName("data")
	val data: List<LandItem?>,

	@field:SerializedName("count")
	val count: Int? = null
)

data class LocationLand(

	@field:SerializedName("lon")
	val lon: Any? = null,

	@field:SerializedName("lat")
	val lat: Any? = null
)

data class User(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class LandItem(

	@field:SerializedName("address")
	val address: AddressLand? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("location")
	val location: LocationLand? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("user")
	val user: User? = null
)

data class AddressLand(

	@field:SerializedName("province")
	val province: String? = null,

	@field:SerializedName("district")
	val district: String? = null,

	@field:SerializedName("regency")
	val regency: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("village")
	val village: String? = null
)
