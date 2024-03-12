package com.example.ramzancalender.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ramzancalender.helper.Resource
import com.example.ramzancalender.models.FilteredModel
import com.example.ramzancalender.network.Repositary
import com.example.ramzancalender.room.PostDb
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainAppViewModel @Inject constructor(
    val postRepositary: Repositary,
) :
    ViewModel() {

    private val _calenderData = MutableStateFlow<Resource<List<FilteredModel>>>(
        Resource.Loading(
            null
        )
    )
    val calenderData: StateFlow<Resource<List<FilteredModel>>> get() = _calenderData
    val combinedList = MutableStateFlow<ArrayList<FilteredModel>>(
        ArrayList()
    )
    var currentObj: FilteredModel? = null


    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchRamzanData(
        latitude: Double,
        longitude: Double,
        method: Int,
        firstMonth: Int,
        secondMonth: Int,
        year: Int
    ) {
        viewModelScope.launch {
            try {
                val firstMonthDeferred =
                    async { fetchDataForMonth(latitude, longitude, method, firstMonth, year) }
                val secondMonthDeferred =
                    async { fetchDataForMonth(latitude, longitude, method, secondMonth, year) }
                val firstMonthData = firstMonthDeferred.await()
                val secondMonthData = secondMonthDeferred.await()
                val combinedData = flowOf(firstMonthData, secondMonthData)
                    .flattenMerge()
                    .toList()
                if (combinedData.all { it is Resource.Success }) {
                    val combinedListData =
                        combinedData.mapNotNull { (it as? Resource.Success)?.data }.flatten()
                    _calenderData.value = Resource.Success(combinedListData)
                    combinedList.value.addAll(combinedListData)
                } else {
                    _calenderData.value = combinedData.first { it !is Resource.Success }
                }
            } catch (e: Exception) {
                _calenderData.value =
                    Resource.Error(throwable = Throwable("Server Busy At The Moment Try Again latter"))
            }
        }
    }

    private suspend fun fetchDataForMonth(
        latitude: Double,
        longitude: Double,
        method: Int,
        month: Int,
        year: Int
    ) = postRepositary.getALlData(latitude, longitude, method, month, year)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchRamadanDataBySchool(
        latitude: Double,
        longitude: Double,
        method: Int,
        firstMonth: Int,
        secondMonth: Int,
        year: Int
    ) {
        viewModelScope.launch {
            try {
                val firstMonthDeferred =
                    async { fetchBySchool(latitude, longitude, method, firstMonth, year) }
                val secondMonthDeferred =
                    async { fetchBySchool(latitude, longitude, method, secondMonth, year) }
                val firstMonthData = firstMonthDeferred.await()
                val secondMonthData = secondMonthDeferred.await()
                val combinedData = flowOf(firstMonthData, secondMonthData)
                    .flattenMerge()
                    .toList()

                if (combinedData.all { it is Resource.Success }) {
                    val combinedListData =
                        combinedData.mapNotNull { (it as? Resource.Success)?.data }.flatten()
                    _calenderData.value = Resource.Success(combinedListData)
                    combinedList.value.addAll(combinedListData)
                } else {
                    _calenderData.value = combinedData.first { it !is Resource.Success }
                }
            } catch (e: Exception) {
                _calenderData.value =
                    Resource.Error(throwable = Throwable("Server Busy At The Moment Try Again latter"))
            }
        }
    }

    private suspend fun fetchBySchool(
        latitude: Double,
        longitude: Double,
        method: Int,
        month: Int,
        year: Int
    ) = postRepositary.getRamzanDataBySchool(latitude, longitude, method, month, year)
}