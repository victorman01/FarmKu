package com.farmkuindonesia.farmku.database.config

import com.farmkuindonesia.farmku.database.responses.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
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

    @Multipart
    @POST("data-collection")
    fun addSoilDataCollection3(
        @Part("nama_varietas") nama_varietas: RequestBody,
        @Part("N") N: RequestBody,
        @Part("P") P: RequestBody,
        @Part("K") K: RequestBody,
        @Part("PH") PH: RequestBody,
        @Part("Longitude") Longitude: RequestBody,
        @Part("Latitude") Latitude: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("Description") Description: RequestBody
    ): Call<AddSoilDataCollectionResponseItem>

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

    @GET("weather")
    fun getWeatherData(
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?,
        @Query("appid") key:String,
        @Query("units") units:String
    ):Call<WeatherResponse>

    @GET("measurement")
    fun getMeasurement(
        @Query("land_id") userId:String
    ):Call<MeasurementResponse>

    @FormUrlEncoded
    @POST("measurement")
    fun addMeasurement(
        @Field("land_id") landId:String,
        @Field("user_id") userId:String
    ):Call<MeasurementResponse>

    @GET("record")
    fun getRecord(
        @Query("measurementId") measurementId:String
    ):Call<RecordResponse>
}