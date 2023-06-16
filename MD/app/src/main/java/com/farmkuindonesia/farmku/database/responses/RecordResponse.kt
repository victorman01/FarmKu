package com.farmkuindonesia.farmku.database.responses

import com.google.gson.annotations.SerializedName

data class RecordResponse(

	@field:SerializedName("records")
	val records: List<RecordsItem?>? = null,

	@field:SerializedName("count")
	val count: Int? = null
)

data class Condition(

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

	@field:SerializedName("ph")
	val ph: String? = null,

	@field:SerializedName("soil_moisture")
	val soilMoisture: String? = null,

	@field:SerializedName("phosphorus")
	val phosphorus: String? = null
)

data class RecordsItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("condition")
	val condition: Condition? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("location")
	val location: Location? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("measurementId")
	val measurementId: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class Location(

	@field:SerializedName("coordinates")
	val coordinates: List<String?>? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("lon")
	val lon: Any? = null,

	@field:SerializedName("lat")
	val lat: Any? = null
)
