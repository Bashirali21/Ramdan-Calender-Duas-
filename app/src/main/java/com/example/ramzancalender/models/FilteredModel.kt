package com.example.ramzancalender.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FilteredModel(
    val sehri: String, val iFtari: String, val hirjriDate: String, val currentData: String,
    val zuharTiming:String,val AsarTime:String,val ishaTime:String,
    val number:Int=0,
    val isCurrentDate:Boolean=false,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)