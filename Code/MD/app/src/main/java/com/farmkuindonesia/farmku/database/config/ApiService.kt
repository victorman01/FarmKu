package com.farmkuindonesia.farmku.database.config

import com.farmkuindonesia.farmku.database.responses.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import java.util.ArrayList

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
        @Query("id") id:String
    ): Call<List<AddressResponseItem>>

    @Multipart
    @POST("preprocess")
    fun addImage(
        @Part image:MultipartBody.Part
    ): Call<DetectionResponse>

    @GET("data-collection")
    fun getSoilDataCollection(): Call<List<SoilDataCollectionResponseItem>>

    @GET("land")
    fun getLandByUserId(
        @Query("user_id") user_id:String
    ):Call<ListLandResponse>

    @GET("record")
    fun getNPKRecord(

    )
}