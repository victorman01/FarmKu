package com.farmkuindonesia.farmku.database.config

import com.farmkuindonesia.farmku.database.responses.SignInResponse
import com.farmkuindonesia.farmku.database.responses.SignUpResponse
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
}