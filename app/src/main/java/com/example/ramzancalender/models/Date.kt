package com.example.ramzancalender.models

data class Date(
    val gregorian: Gregorian,
    val hijri: Hijri,
    val readable: String,
    val timestamp: String
)