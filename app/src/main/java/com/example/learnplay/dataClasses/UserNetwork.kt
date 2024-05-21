package com.example.learnplay.dataClasses



data class UserNetwork (
    val userName:String,
    val exp:Int,
    val multiplier:Float ,
    val rankPlace:Int ,
    val achievement: List<List<Any>>
)

data class AchievementNet(
    val name: String,
    val value1: Int,
    val value2: Int
)
{

}