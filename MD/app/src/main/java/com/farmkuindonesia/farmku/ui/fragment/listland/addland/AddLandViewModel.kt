package com.farmkuindonesia.farmku.ui.fragment.listland.addland

import androidx.lifecycle.ViewModel
import com.farmkuindonesia.farmku.database.Repository
import com.farmkuindonesia.farmku.database.responses.AddLandResponse
import com.farmkuindonesia.farmku.database.responses.LocationAddLand

class AddLandViewModel(private val rep: Repository) : ViewModel() {
    val messages = rep.messages
    val varietyList = rep.varietyList
    val successCheck = rep.success
    fun getVariety() = rep.getVariety()
    fun getProvince() = rep.getAllAddress("province", "0")
    fun getDistrict(id: String) = rep.getAllAddress("district", id)
    fun getRegency(id: String) = rep.getAllAddress("regency", id)
    fun getVillage(id: String) = rep.getAllAddress("village", id)
    fun getIdUser() = rep.getUser()
    fun addNewLand(request:AddLandResponse) = rep.addNewLand(request)
}