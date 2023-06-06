package com.farmkuindonesia.farmku.database.responses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AddressResponse(

	@field:SerializedName("AddressResponse")
	val addressResponse: List<AddressResponseItem?>? = null

) : Parcelable

@Parcelize
data class AddressResponseItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: String? = null

) : Parcelable
