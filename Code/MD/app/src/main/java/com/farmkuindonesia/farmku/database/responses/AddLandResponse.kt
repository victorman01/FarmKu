package com.farmkuindonesia.farmku.database.responses

import com.google.gson.annotations.SerializedName

data class AddLandResponse(

    @field:SerializedName("area")
    val area: Int? = null,

    @field:SerializedName("variety_id")
    val varietyId: String? = null,

    @field:SerializedName("user_id")
    val userId: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("address_id")
    val addressId: String? = null,

    @field:SerializedName("location")
    val location: LocationAddLand? = null
)

data class LocationAddLand(

    @field:SerializedName("lat")
    var lat: Double? = null,

    @field:SerializedName("lon")
    var lon: Double? = null

)
