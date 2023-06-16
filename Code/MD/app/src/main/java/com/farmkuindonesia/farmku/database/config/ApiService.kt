package com.farmkuindonesia.farmku.database.config

import com.farmkuindonesia.farmku.database.responses.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("/auth/signin")
    fun signIn(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<SignInResponse>

    @FormUrlEncoded
    @POST("/auth/signup")
    fun signUp(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("phone_number") phone_number: String,
        @Field("address") address: String,
    ): Call<SignUpResponse>

    @GET("address")
    fun getAllProvince(
        @Query("search") search: String,
        @Query("id") id: String
    ): Call<List<AddressResponseItem>>

    @Multipart
    @POST("preprocess")
    fun addImage(
        @Part image: MultipartBody.Part
    ): Call<DetectionResponse>

    @GET("data-collection")
    fun getSoilDataCollection(): Call<List<SoilDataCollectionResponseItem>>

    @FormUrlEncoded
    @Multipart
    @POST("data-collection")
    fun addSoilDataCollection(
        @Part Image: MultipartBody.Part,
        @Field("nama_varietas") nama_varietas: String,
        @Field("N") N: Double,
        @Field("P") P: Double,
        @Field("K") K: Double,
        @Field("PH") PH: Double,
        @Field("Longitude") Longitude: Double,
        @Field("Latitude") Latitude: Double,
        @Field("Description") Description: String,
    ): Call<SoilDataCollectionResponseItem>

    @GET("land")
    fun getLandByUserId(
        @Query("user_id") user_id: String?
    ): Call<ListLandResponse>

    @GET("variety")
    fun getVariety(): Call<VaerityResponse>


    @POST("land")
    fun addNewLand(
        @Body request: AddLandResponse
    ): Call<AddLandResponse>
}