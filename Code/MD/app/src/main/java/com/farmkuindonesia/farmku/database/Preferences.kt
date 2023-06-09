package com.farmkuindonesia.farmku.database

import android.content.Context
import android.content.SharedPreferences
import com.farmkuindonesia.farmku.database.model.Address
import com.farmkuindonesia.farmku.database.model.User

class Preferences(context: Context) {
    private val preferences =
        context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)

    fun setLogin(user: User?, loggedInWith: String) {
        val editor = preferences.edit()
        editor.putString(USER_ID, user?.id)
        editor.putString(USER_NAME, user?.name)
        editor.putString(USER_EMAIL, user?.email)
        editor.putString(USER_PHONE_NUMBER, user?.phoneNumber)
        editor.putString(USER_ADDRESS_ID, user?.address?.id)
        editor.putString(USER_ADDRESS_PROVINCE, user?.address?.province)
        editor.putString(USER_ADDRESS_DISTRICT, user?.address?.district)
        editor.putString(USER_ADDRESS_REGENCY, user?.address?.regency)
        editor.putString(USER_ADDRESS_VILLAGE, user?.address?.village)
        editor.putString(USER_ROLE, user?.role)
        editor.putString(USER_CREATED_AT, user?.createdAt)
        editor.putString(USER_UPDATED_AT, user?.updatedAt)
        editor.putString(LOGGEDINWITH, loggedInWith)
        editor.apply()
    }

    fun setLogout() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }

    fun getUser(): User {
        val userId = preferences.getString(USER_ID, "") ?: ""
        val userName = preferences.getString(USER_NAME, "") ?: ""
        val userEmail = preferences.getString(USER_EMAIL, "") ?: ""
        val userPhoneNumber = preferences.getString(USER_PHONE_NUMBER, "") ?: ""
        val userAddressId = preferences.getString(USER_ADDRESS_ID, "") ?: ""
        val userAddressProvince = preferences.getString(USER_ADDRESS_PROVINCE, "") ?: ""
        val userAddressDistrict = preferences.getString(USER_ADDRESS_DISTRICT, "") ?: ""
        val userAddressRegency = preferences.getString(USER_ADDRESS_REGENCY, "") ?: ""
        val userAddressVillage = preferences.getString(USER_ADDRESS_VILLAGE, "") ?: ""
        val userRole = preferences.getString(USER_ROLE, "") ?: ""
        val userCreatedAt = preferences.getString(USER_CREATED_AT, "") ?: ""
        val userUpdatedAt = preferences.getString(USER_UPDATED_AT, "") ?: ""

        return User(
            id = userId,
            name = userName,
            email = userEmail,
            phoneNumber = userPhoneNumber,
            address = Address(
                id = userAddressId,
                province = userAddressProvince,
                district = userAddressDistrict,
                regency = userAddressRegency,
                village = userAddressVillage
            ),
            role = userRole,
            createdAt = userCreatedAt,
            updatedAt = userUpdatedAt
        )
    }

    companion object {
        private const val USER_ID = "userId"
        const val USER_NAME = "userName"
        private const val USER_EMAIL = "userEmail"
        private const val USER_PHONE_NUMBER = "userPhoneNumber"
        private const val USER_ADDRESS_ID = "userAddressId"
        private const val USER_ADDRESS_PROVINCE = "userAddressProvince"
        private const val USER_ADDRESS_DISTRICT = "userAddressDistrict"
        private const val USER_ADDRESS_REGENCY = "userAddressRegency"
        private const val USER_ADDRESS_VILLAGE = "userAddressVillage"
        private const val USER_ROLE = "userRole"
        private const val USER_CREATED_AT = "userCreatedAt"
        private const val USER_UPDATED_AT = "userUpdatedAt"
        const val LOGGEDINWITH = "loggedinwith"
        val PREFERENCES = "pref"

        private var instance: Preferences? = null

        fun getInstance(context: Context): Preferences =
            instance ?: synchronized(this) {
                instance ?: Preferences(context)
            }

        fun setLogout(preferences: SharedPreferences) {
            val editor = preferences.edit()
            editor.clear()
            editor.apply()
        }
    }
}