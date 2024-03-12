package com.example.ramzancalender.network

import com.example.ramzancalender.models.RamzanData
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PrayerTimesApi {
    @GET("v1/calendar")
   suspend fun getRamzanData(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("method") method: Int,
        @Query("month") month: Int,
        @Query("year") year: Int
    ): RamzanData

    @GET("v1/calendar")
    suspend fun getRamzanDataBySchool(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("method") method: Int,
        @Query("month") month: Int,
        @Query("year") year: Int,
        @Query("school") school:Int
    ): RamzanData
}