package com.example.learnplay.fragments

import android.content.Intent
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
import com.example.learnplay.ApiClient
import com.example.learnplay.R
import com.example.learnplay.activities.TasksChallenging
import com.example.learnplay.viewModels.TasksViewModel
import com.example.learnplay.dataClasses.Topic
import com.example.learnplay.adapters.TopicsAdapter
import com.example.learnplay.dataClasses.Quest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TasksFg : Fragment() {

    companion object {
        fun newInstance() = TasksFg()
    }

    private lateinit var viewModel: TasksViewModel
    private val topicsList = listOf(
        Topic("1. Вероятность и статистика", 0),
        Topic("2. Алгебраические выражения. Степени и корни", 0),
        Topic("3. Анализ геометрических высказываний", 0),
        Topic("4. Расчёты по формулам", 0),
        Topic("5. Практические расчетные задачи", 0),
        Topic("6. Неравенства, координатная прямая", 0),
        Topic("7. Уравнения и системы уравнений (1 часть)", 0),
        Topic("8. Графики функций", 0),
        Topic("9. Неравенства и системы неравенств (1 часть)", 0),
        Topic("10. Вычисления и преобразования", 0),
        Topic("11. Алгебраические выражения. ФСУ", 0),
        Topic("12. Прогрессии", 0),
        Topic("13. Треугольники", 0),
        Topic("14. Прямоугольный треугольник", 0),
        Topic("15. Четырехугольники", 0),
        Topic("16. Окружность", 0),
        Topic("17. Площади фигур", 0),
        Topic("18. Фигуры на квадратной решетки", 0)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Создание макета фрагмента
        val view = inflater.inflate(R.layout.fragment_tasks_fg, container, false)

        // Инициализация кнопки для рандомного задания
        val randomButton = view.findViewById<Button>(R.id.randomButton)
        randomButton.setOnClickListener {
            startRandomQuest()
        }

        // Инициализация кнопки для ежедневного задания
        val dailyButton = view.findViewById<Button>(R.id.dailyButton)
        dailyButton.setOnClickListener {
            startDailyQuest()
        }

        // Инициализация списка тем
        val topicsListView = view.findViewById<ListView>(R.id.topicsListView)
        val topicsAdapter = TopicsAdapter(requireContext(), topicsList) // Предполагается, что у вас есть список тем и адаптер для них
        topicsListView.adapter = topicsAdapter


        val selectedTopics = mutableListOf<String>()
        // Инициализация кнопки для создания квеста
        val createQuestButton = view.findViewById<Button>(R.id.createQuestButton)
        createQuestButton.setOnClickListener {
            startUserQuest()
        }
        return view
    }

    private fun startUserQuest() {
        val selectedTaskCounts = topicsList.map { it.taskCount }

        if (selectedTaskCounts.all { it == 0 }) {
            Toast.makeText(context, "Выберите задания", Toast.LENGTH_SHORT).show()
        } else {
            val request = QuestRequest(selectedTaskCounts)
            ApiClient.apiService.startUserQuest(request).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        // Обработка успешного ответа
                        Toast.makeText(context, "Квест создан", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(context, TasksChallenging::class.java))
                    } else {
                        // Обработка ошибки
                        Toast.makeText(context, "Ошибка создания квеста", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Обработка ошибки
                    Toast.makeText(context, "Ошибка: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun startDailyQuest() {
        val call = ApiClient.apiService.startDailyQuest()
        call.enqueue(object : Callback<Quest> {
            override fun onResponse(call: Call<Quest>, response: Response<Quest>) {
                if (response.isSuccessful) {
                    val quest = response.body()
                    // обработка успешного ответа
                    startActivity(Intent(context,TasksChallenging::class.java))
                } else {
                    Toast.makeText(context, "Ошибка сервера: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Quest>, t: Throwable) {
                Toast.makeText(context, "Ошибка сети: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun startRandomQuest() {
        val call = ApiClient.apiService.startRandomQuest()
        call.enqueue(object : Callback<Quest> {
            override fun onResponse(call: Call<Quest>, response: Response<Quest>) {
                if (response.isSuccessful) {
                    val quest = response.body()
                    // обработка успешного ответа
                    startActivity(Intent(context,TasksChallenging::class.java))
                } else {
                    Toast.makeText(context, "Ошибка сервера: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Quest>, t: Throwable) {
                Toast.makeText(context, "Ошибка сети: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}


data class QuestRequest(
    val listTask: List<Int>
)