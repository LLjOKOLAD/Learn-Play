package com.example.learnplay

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import android.widget.Toast

class Tasks : Fragment() {

    companion object {
        fun newInstance() = Tasks()
    }

    private lateinit var viewModel: TasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Создание макета фрагмента
        val view = inflater.inflate(R.layout.fragment_tasks, container, false)

        // Инициализация кнопки для рандомного задания
        val randomButton = view.findViewById<Button>(R.id.randomButton)
        randomButton.setOnClickListener {
            // Логика для обработки нажатия кнопки рандомного задания
        }

        // Инициализация кнопки для ежедневного задания
        val dailyButton = view.findViewById<Button>(R.id.dailyButton)
        dailyButton.setOnClickListener {
            // Логика для обработки нажатия кнопки ежедневного задания
        }

        val topicsList = listOf(
            Topic("Тема 1", 0),
            Topic("Тема 2", 0),
            Topic("Тема 3", 0)
        )

        // Инициализация списка тем
        val topicsListView = view.findViewById<ListView>(R.id.topicsListView)
        val topicsAdapter = TopicsAdapter(requireContext(), topicsList) // Предполагается, что у вас есть список тем и адаптер для них
        topicsListView.adapter = topicsAdapter


        val selectedTopics = mutableListOf<String>()
        // Инициализация кнопки для создания квеста
        val createQuestButton = view.findViewById<Button>(R.id.createQuestButton)
        createQuestButton.setOnClickListener {
            // Собираем информацию о выбранных темах
            val selectedTopicsInfo = StringBuilder()
            selectedTopicsInfo.append("Выбранные темы:\n")
            for (topic in topicsList) {
                if (topic.taskCount > 0) {
                    selectedTopicsInfo.append("${topic.name}: ${topic.taskCount} задач\n")
                    // Если нужно сохранить выбранные темы в список для дальнейшего использования
                    selectedTopics.add(topic.name)
                }
            }

            // Выводим информацию о выбранных темах
            if (selectedTopics.isNotEmpty()) {
                // Если есть выбранные темы
                // Можно, например, вывести информацию в лог
                Log.d("SelectedTopics", selectedTopicsInfo.toString())
                // Или вывести всплывающее сообщение
                Toast.makeText(context, selectedTopicsInfo.toString(), Toast.LENGTH_SHORT).show()
            } else {
                // Если темы не выбраны
                Toast.makeText(context, "Выберите темы для создания квеста", Toast.LENGTH_SHORT).show()
            }
        }



        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TasksViewModel::class.java)
        // TODO: Use the ViewModel
    }

}