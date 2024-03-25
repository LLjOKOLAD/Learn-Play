package com.example.learnplay

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Button
import android.view.KeyEvent
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

class ProfileFg : Fragment() {

    private lateinit var textView: TextView
    private lateinit var editText: EditText


    companion object {
        fun newInstance() = ProfileFg()

    }

    private lateinit var viewModel: ProfileFgViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Код для достижений
        val view = inflater.inflate(R.layout.fragment_profile_fg, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewAchievements)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = AchievementAdapter(getAchievements())

        // Находим TextView и EditText в макете фрагмента
        textView = view.findViewById(R.id.textView)
        editText = view.findViewById(R.id.editText)

        val changeNambut: ImageButton = view.findViewById(R.id.changeNameButton)

        // Устанавливаем слушатель кликов на TextView
        textView.setOnClickListener {
            // Скрываем TextView и показываем EditText
            textView.visibility = View.INVISIBLE
            editText.visibility = View.VISIBLE
            editText.setText(textView.text.toString())
            editText.requestFocus() // Переводим фокус на EditText
        }

        // Обработчик события нажатия клавиши "Готово" на клавиатуре
        changeNambut.setOnClickListener {
            // Получаем текст из EditText
            val newText = editText.text.toString()
            // Устанавливаем новый текст в TextView
            textView.text = newText

            // Показываем TextView и скрываем EditText
            textView.visibility = View.VISIBLE
            editText.visibility = View.INVISIBLE

        }




        return view
    }


    private fun getAchievements(): List<Achievement> {
        // Загрузка данных для списка достижений (например, из базы данных или ресурсов)
        // Возвращайте список объектов типа Achievement
        return listOf(
            Achievement("Достижение 1", "Описание достижения 1", 50,100),
            Achievement("Достижение 2", "Описание достижения 2", 30,100),
            Achievement("Достижение 3", "Описание достижения 3", 70,100),
            Achievement("Достижение 4", "Описание достижения 4", 10,100)
        )
    }


}