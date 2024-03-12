package com.example.ramzancalender.network


import android.util.Log
import com.example.ramzancalender.helper.NetworkHelper
import com.example.ramzancalender.helper.networkBoundResource
import com.example.ramzancalender.models.FilteredModel
import com.example.ramzancalender.room.PostDb
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class RepositaryImpl @Inject constructor(
    val apiService: PrayerTimesApi, val db: PostDb, val networkHelper: NetworkHelper
) : Repositary {
    private val restaurantDao = db.dao
    override suspend fun getALlData(
        latitude: Double,
        longitude: Double,
        method: Int, month: Int,
        year: Int
    ) = networkBoundResource(query = {
        restaurantDao.getAllRamzanData()
    }, fetch = {
        apiService.getRamzanData(latitude, longitude, method, month, year).data
    }, saveFetchResult = { restaurants ->
        val currentDate =
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(
                Date()
            )
        val doesDelete = restaurants.filter { it.date.readable.contains("Mar") }
        if (doesDelete.isNotEmpty()) {
            restaurantDao.deleteAllRestaurants()
        }
        restaurantDao.insertIntoRamzanDb(restaurants.distinctBy { it.date.readable }.map { data ->
            FilteredModel(
                data.timings.Fajr.replace("(GMT)", "Am").trim(),
                data.timings.Maghrib.replace("(GMT)", "pm").trim(),
                data.date.hijri.month.en + " " + data.date.hijri.day,
                data.date.readable,
                zuharTiming = data.timings.Dhuhr,
                AsarTime = data.timings.Asr,
                ishaTime = data.timings.Isha,
                isCurrentDate = data.date.readable == currentDate,
                number = data.date.hijri.day.toInt()
            )
        })
    }, shouldFetch = {
        Log.d("messsag", networkHelper.isNetworkAvailable().toString())
        networkHelper.isNetworkAvailable()
    }, onFetchFailed = {
        Log.d("sizess", it.stackTrace.toString())

    })

    override suspend fun getALlDataMonth2(
        latitude: Double,
        longitude: Double,
        method: Int, month: Int,
        year: Int
    ) = networkBoundResource(query = {
        restaurantDao.getAllRamzanData()
    }, fetch = {
        apiService.getRamzanData(latitude, longitude, method, month, year).data
    }, saveFetchResult = { restaurants ->
        val currentDate =
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(
                Date()
            )
        val doesDelete = restaurants.filter { it.date.readable.contains("Apr") }
        if (doesDelete.isNotEmpty()) {
            restaurantDao.deleteAllRestaurants()
        }

        restaurantDao.insertIntoRamzanDb(restaurants.map { data ->
            FilteredModel(
                data.timings.Fajr.replace("(GMT)", "Am").trim(),
                data.timings.Maghrib.replace("(GMT)", "pm").trim(),
                data.date.hijri.month.en + " " + data.date.hijri.day,
                data.date.readable,
                zuharTiming = data.timings.Dhuhr,
                AsarTime = data.timings.Asr,
                ishaTime = data.timings.Isha,
                isCurrentDate = data.date.readable == currentDate,
                number = data.date.hijri.day.toInt()
            )
        })
    }, shouldFetch = {
        networkHelper.isNetworkAvailable()
    }, onFetchFailed = {
    })

    override suspend fun getRamzanDataBySchool(
        latitude: Double,
        longitude: Double,
        method: Int,
        month: Int,
        year: Int,
        school: Int
    ) = networkBoundResource(query = {
        restaurantDao.getAllRamzanData()
    }, fetch = {
        apiService.getRamzanDataBySchool(latitude, longitude, method, month, year, school).data
    }, saveFetchResult = { restaurants ->
        val currentDate =
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(
                Date()
            )
        val doesDelete = restaurants.filter { it.date.readable.contains("Apr") }
        if (doesDelete.isNotEmpty()) {
            restaurantDao.deleteAllRestaurants()
        }
        restaurantDao.insertIntoRamzanDb(restaurants.map { data ->
            FilteredModel(
                data.timings.Fajr.replace("(GMT)", "Am").trim(),
                data.timings.Maghrib.replace("(GMT)", "pm").trim(),
                data.date.hijri.month.en + " " + data.date.hijri.day,
                data.date.readable,
                zuharTiming = data.timings.Dhuhr,
                AsarTime = data.timings.Asr,
                ishaTime = data.timings.Isha,
                isCurrentDate = data.date.readable == currentDate,
                number = data.date.hijri.day.toInt()
            )
        })
    }, shouldFetch = {
        networkHelper.isNetworkAvailable()
    }, onFetchFailed = {
    })


}