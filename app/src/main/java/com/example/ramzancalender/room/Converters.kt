package com.example.ramzancalender.room

import androidx.room.TypeConverter
import com.example.ramzancalender.models.FilteredModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Converters {

    @TypeConverter
    fun fromResultList(resultList: List<FilteredModel>): String {
        val gson = Gson()
        return gson.toJson(resultList)
    }

    @TypeConverter
    fun toResultList(resultListString: String): List<FilteredModel> {
        val gson = Gson()
        val type = object : TypeToken<List<FilteredModel>>() {}.type
        return gson.fromJson(resultListString, type)
    }

}