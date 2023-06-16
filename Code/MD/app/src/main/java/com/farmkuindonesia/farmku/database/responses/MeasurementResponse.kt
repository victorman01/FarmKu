package com.farmkuindonesia.farmku.database.responses

import com.google.gson.annotations.SerializedName

data class MeasurementResponse(

	@field:SerializedName("data")
	val data: List<ItemData?>? = null,

	@field:SerializedName("count")
	val count: Int? = null
)

data class ItemData(

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("start")
	val start: Any? = null,

	@field:SerializedName("land")
	val land: Land? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("end")
	val end: Any? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("device")
	val device: Device? = null
)

data class Device(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("state")
	val state: String? = null
)

data class LocationMeasurement(

	@field:SerializedName("lon")
	val lon: Any? = null,

	@field:SerializedName("lat")
	val lat: Any? = null
)

data class Land(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("location")
	val location: LocationMeasurement? = null,

	@field:SerializedName("id")
	val id: String? = null
)
