package com.example.learnplay.dataClasses

data class Task(
    val idTask: Long,
    val idTopic: Long,
    val quotation: String,
    val addText: String?, // Поле может быть null, поэтому тип String с символом вопроса
    val answer: String,
    val exp: Int,
    val image: String? // Поле может быть null, поэтому тип String с символом вопроса
) {
    // Метод для получения значения цитаты
    fun getQuotation1(): String {
        return quotation
    }
}
