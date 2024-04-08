package com.example.learnplay

class Topic(val name: String, var taskCount: Int) {

    fun increaseTaskCount() {
        taskCount++
    }

    fun decreaseTaskCount() {
        if (taskCount > 0) {
            taskCount--
        }
    }
}
