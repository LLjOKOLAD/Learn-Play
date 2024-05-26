package com.example.learnplay.dataClasses

data class TaskResponse(
    val idTask: Int,
    val topic: TopicResponse,
    val addText: String?,
    val answer: String,
    val exp: Int,
    val image: String?,
    val quotation: String
)

data class TopicResponse(
    val idTopic: Int,
    val nameTopic: String,
    val countTask: Int
)