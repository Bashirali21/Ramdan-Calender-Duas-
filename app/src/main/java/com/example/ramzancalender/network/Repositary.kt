package com.example.ramzancalender.network

import com.example.ramzancalender.helper.Resource
import com.example.ramzancalender.models.FilteredModel
import kotlinx.coroutines.flow.Flow

interface Repositary {
    suspend fun getALlData(
        latitude: Double,
        longitude: Double,
        method: Int, month: Int,
        year: Int
    ): Flow<Resource<List<FilteredModel>>>

    suspend fun getALlDataMonth2(
        latitude: Double,
        longitude: Double,
        method: Int, month: Int,
        year: Int
    ): Flow<Resource<List<FilteredModel>>>

    suspend fun getRamzanDataBySchool(
        latitude: Double,
        longitude: Double,
        method: Int, month: Int,
        year: Int,
        school:Int=1
    ): Flow<Resource<List<FilteredModel>>>
}