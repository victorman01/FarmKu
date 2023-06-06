package com.farmkuindonesia.farmku.database

import android.os.Build
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.farmkuindonesia.farmku.database.config.ApiService
import com.farmkuindonesia.farmku.database.model.Address
import com.farmkuindonesia.farmku.database.model.User
import com.farmkuindonesia.farmku.database.responses.AddressResponse
import com.farmkuindonesia.farmku.database.responses.AddressResponseItem
import com.farmkuindonesia.farmku.database.responses.SignInResponse
import com.farmkuindonesia.farmku.database.responses.SignUpResponse
import com.farmkuindonesia.farmku.utils.event.Event
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Repository constructor(private val apiService: ApiService, private val pref: Preferences) {
    private val _messages = MutableLiveData<Event<String>>()
    val messages: LiveData<Event<String>> = _messages

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userLogin = MutableLiveData<User>()
    val userLogin: LiveData<User> = _userLogin

    //Login
    fun signIn(email: String, password: String): LiveData<SignInResponse> {
//        _isLoading.value = true
        val signInResponse = MutableLiveData<SignInResponse>()
        val client = apiService.signIn(email, password)
        client.enqueue(object : Callback<SignInResponse> {
            override fun onResponse(
                call: Call<SignInResponse>, response: Response<SignInResponse>
            ) {
//                _isLoading.value = false
                if (response.isSuccessful) {
                    signInResponse.value = response.body()

                    if (signInResponse.value?.token != null) {
                        val decodedJwt = decodeJWT(signInResponse.value?.token.toString())
                        val userData = JSONObject(decodedJwt).getString("user")
                        val userAddress = JSONObject(userData).getString("address")
                        val user = User(
                            JSONObject(userData).getString("id"),
                            JSONObject(userData).getString("name"),
                            JSONObject(userData).getString("email"),
                            JSONObject(userData).getString("phone_number"),
                            Address(
                                JSONObject(userAddress).getString("id"),
                                JSONObject(userAddress).getString("province"),
                                JSONObject(userAddress).getString("district"),
                                JSONObject(userAddress).getString("regency"),
                                JSONObject(userAddress).getString("village")
                            ),
                            JSONObject(userData).getString("role"),
                            JSONObject(userData).getString("created_at"),
                            JSONObject(userData).getString("updated_at")
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
//                _isLoading.value = false
            }
        })
        return signInResponse
    }

    private fun decodeJWT(jwt: String): String {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return "Requires SDK 26"
        val parts = jwt.split(".")
        return try {
            val charset = charset("UTF-8")
            val payload = String(Base64.getUrlDecoder().decode(parts[1].toByteArray(charset)), charset)
            payload
        } catch (e: Exception) {
            "Error parsing JWT: $e"
        }
    }

    fun setUser(user: User?) = pref.setLogin(user)

    // Register
    fun signUp(name: String, email: String, address: String, phone: String, password: String): LiveData<SignUpResponse>{
//        _isLoading.value = true
        val signUpResponse = MutableLiveData<SignUpResponse>()
        val client = apiService.signUp(name, email, password, phone, address)
        client.enqueue(object : Callback<SignUpResponse> {
            override fun onResponse(
                call: Call<SignUpResponse>, response: Response<SignUpResponse>
            ) {
//                _isLoading.value = false
                if (response.isSuccessful) {
                    signUpResponse.value = response.body()
                    if (signUpResponse.value?.id == null) {
                        _messages.value = Event(signUpResponse.value?.message.toString())
                    }
                } else {
                    _messages.value = Event("Register Failed. Message: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                _messages.value = Event("$TAG, ${t.message.toString()}")
//                _isLoading.value = false
            }
        })
        return signUpResponse
    }

    fun getAllProvince(): LiveData<List<AddressResponseItem?>> {
        val addressResponse = MutableLiveData<List<AddressResponseItem?>>()

        val client = apiService.getAllProvince("province")
        client.enqueue(object : Callback<List<AddressResponseItem>> {
            override fun onResponse(
                call: Call<List<AddressResponseItem>>,
                response: Response<List<AddressResponseItem>>
            ) {
                if (response.isSuccessful) {
                    addressResponse.value = response.body()
                } else {
                    _messages.value = Event("Data Province is missing. Message: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<AddressResponseItem>>, t: Throwable) {
                _messages.value = Event("$TAG, ${t.message.toString()}")
            }
        })

        return addressResponse
    }

    companion object {
        private const val TAG = "Repository"
        private var instance: Repository? = null

        fun getInstance(pref: Preferences, apiService: ApiService): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, pref)
            }.also { instance = it }
    }
}

