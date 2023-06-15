package com.farmkuindonesia.farmku.database.responses

import com.google.gson.annotations.SerializedName

data class VaerityResponse(

	@field:SerializedName("data")
	val data: List<DataItemVariety>? = null,

	@field:SerializedName("count")
	val count: Int? = null
)

data class Image(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class OptimalCondition(

	@field:SerializedName("rainfall")
	val rainfall: String? = null,

	@field:SerializedName("potassium")
	val potassium: String? = null,

	@field:SerializedName("soil_temperature")
	val soilTemperature: String? = null,

	@field:SerializedName("light")
	val light: String? = null,

	@field:SerializedName("nitrogen")
	val nitrogen: String? = null,

	@field:SerializedName("pH")
	val pH: String? = null,

	@field:SerializedName("soil_moisture")
	val soilMoisture: String? = null,

	@field:SerializedName("phosphorus")
	val phosphorus: String? = null
)

data class DataItemVariety(

	@field:SerializedName("image")
	val image: Image? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("optimal_condition")
	val optimalCondition: OptimalCondition? = null,

	@field:SerializedName("plant")
	val plant: Plant? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)

data class Plant(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)
