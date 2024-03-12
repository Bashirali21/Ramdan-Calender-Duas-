package com.example.ramzancalender.models

data class Dua(
    val title: String,
    val arabic: String,
    val latin: String,
    val translation: String,
    val notes: String?,
    val fawaid: String,
    val source: String
)