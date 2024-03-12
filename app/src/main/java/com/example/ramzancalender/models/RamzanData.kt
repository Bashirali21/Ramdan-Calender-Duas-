package com.example.ramzancalender.models

import androidx.room.Entity
import androidx.room.PrimaryKey

data class RamzanData(
    val code: Int,
    val data: List<Data>,
    val status: String,

)