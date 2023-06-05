package com.farmkuindonesia.farmku.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.auth0.android.jwt.JWT
import com.farmkuindonesia.farmku.database.config.ApiService
import com.farmkuindonesia.farmku.database.model.Address
import com.farmkuindonesia.farmku.database.model.User
import com.farmkuindonesia.farmku.database.responses.SignInResponse
import com.farmkuindonesia.farmku.utils.event.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository constructor(private val apiService: ApiService, private val pref: Preferences) {
    private val _messages = MutableLiveData<Event<String>>()
    val messages: LiveData<Event<String>> = _messages

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userLogin = MutableLiveData<User>()
    val userLogin: LiveData<User> = _userLogin

    fun signIn(email: String, password: String): LiveData<SignInResponse> {
        _isLoading.value = true
        val signInResponse = MutableLiveData<SignInResponse>()
        val client = apiService.signIn(email, password)
        client.enqueue(object : Callback<SignInResponse> {
            override fun onResponse(
                call: Call<SignInResponse>, response: Response<SignInResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    signInResponse.value = response.body()

                    if (signInResponse.value?.token != null) {
                        val jwt = decodeJWT(signInResponse.value?.token.toString())
                        val user = User(
                            jwt.getClaim("user.id").asString(),
                            jwt.getClaim("user.name").asString(),
                            jwt.getClaim("user.email").asString(),
                            jwt.getClaim("user.phone_number").asString(),
                            Address(
                                jwt.getClaim("user.address.id").asString(),
                                jwt.getClaim("user.address.province").asString(),
                                jwt.getClaim("user.address.district").asString(),
                                jwt.getClaim("user.address.regency").asString(),
                                jwt.getClaim("user.address.village").asString()
                            ),
                            jwt.getClaim("user.role").asString(),
                            jwt.getClaim("user.created_at").asString(),
                            jwt.getClaim("user.updated_at").asString()
                        )
                        _userLogin.value = user
                        _messages.value = Event("Login Success, Hello ${user.name}")
                } else {
                        _messages.value = Event("Token is missing")
                    }
                } else {
                    _messages.value = Event("Login Failed. Message: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SignInResponse>, t: Throwable) {
                _messages.value = Event("$TAG, ${t.message.toString()}")
                _isLoading.value = false
            }
        })
        return signInResponse
    }

    private fun decodeJWT(jwtToken: String): JWT {
        return JWT(jwtToken)
    }

    fun setUser(user: User?) = pref.setLogin(user)

    companion object {
        private const val TAG = "Repository"
        private var instance: Repository? = null

        fun getInstance(pref: Preferences, apiService: ApiService): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, pref)
            }.also { instance = it }
    }
}

