// Achievement.kt
package com.example.learnplay.dataClasses


data class Achievement(
    val title: String,
    val description: String,
    val progress: Int,
    val maxProgress: Int
)
