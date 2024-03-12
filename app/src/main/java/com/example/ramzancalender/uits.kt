package com.example.ramzancalender

import android.content.SharedPreferences
import com.example.ramzancalender.fragments.sunni

fun SharedPreferences.Editor.putLatLng(latitude: Double, longitude: Double) {
    this.putFloat("latitude", latitude.toFloat())
    this.putFloat("longitude", longitude.toFloat())
    apply()
}

// Extension function to get latitude and longitude from SharedPreferences
fun SharedPreferences.getLatLng(): Pair<Double, Double> {
    val latitude = this.getFloat("latitude", 0.0f).toDouble()
    val longitude = this.getFloat("longitude", 0.0f).toDouble()
    return Pair(latitude, longitude)
}
fun SharedPreferences.Editor.putSect(sect:String) {
    this.putString("sect", sect)
    apply()
}
fun SharedPreferences.getSect(): String {
    val sect = this.getString("sect", sunni)
    return sect!!
}

fun SharedPreferences.Editor.putCity(city:String) {
    this.putString("city", city)
    apply()
}
fun SharedPreferences.getCity(): String {
    val sect = this.getString("city", "")
    return sect!!
}

fun extractTime(input: String): String {
    // Define a regular expression pattern to match the time format (HH:mm)
    val pattern = Regex("\\b\\d{1,2}:\\d{2}\\b")

    // Find the first match in the input string
    val matchResult = pattern.find(input)

    // Return the matched time if found, otherwise return an empty string
    return matchResult?.value ?: ""
}