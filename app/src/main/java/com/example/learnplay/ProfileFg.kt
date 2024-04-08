package com.example.learnplay

import android.annotation.SuppressLint
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
import android.widget.ImageView
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

        val db = DbHelper(requireContext(),null)
        val user = db.getLogUser()


        if (user != null && user.character.isEmpty()) {
            val intent = Intent(requireContext(), CharacterSelectionActivity::class.java)
            startActivity(intent)
        }

        //Код для достижений
        val view = inflater.inflate(R.layout.fragment_profile_fg, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewAchievements)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = AchievementAdapter(getAchievements())


        // Находим TextView и EditText в макете фрагмента
        textView = view.findViewById(R.id.textView)
        editText = view.findViewById(R.id.editText)

        if (user != null) {
            textView.text = user.name
        }

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

            user?.name = newText
            if (user != null) {
                db.updateUser(user)
            }

        }



        val multPerstext: TextView = view.findViewById(R.id.multPers)
        val expAmmtext: TextView = view.findViewById(R.id.expAmm)

        if (user != null) {
            multPerstext.setText(String.format("%.0f%%", user.multiplier * 100))
            expAmmtext.setText(user.experience.toString())
        }

        val iconLige: ImageView = view.findViewById(R.id.ligeIcon)

        val exp: Int? = user?.experience
        if (exp != null) {
            if (exp in 0 .. 19){
                iconLige.setImageResource(R.drawable.lige_1)
            }
            else if (exp in 20..59){
                iconLige.setImageResource(R.drawable.lige_2)
            }
            else if (exp in 60 .. 119){
                iconLige.setImageResource(R.drawable.lige_3)
            }
            else if (exp in 120 .. 199){
                iconLige.setImageResource(R.drawable.lige_4)
            }
            else if (exp in 200 .. 299){
                iconLige.setImageResource(R.drawable.lige_5)
            }
            else if (exp in 300 .. 419){
                iconLige.setImageResource(R.drawable.lige_6)
            }
            else if (exp >= 420){
                iconLige.setImageResource(R.drawable.lige_7)
            }

            val avaCh: ImageButton = view.findViewById(R.id.changeAvaButton)

            avaCh.setOnClickListener {
                val intent = Intent(requireContext(), CharacterSelectionActivity::class.java)
                startActivity(intent)
            }


        }
        return view
    }


    private fun getAchievements(): List<Achievement> {
        // Загрузка данных для списка достижений (например, из базы данных или ресурсов)
        // Возвращайте список объектов типа Achievement
        return listOf(
            Achievement("Достижение 1", "Описание достижения 1", 50,100),
            Achievement("Достижение 2", "Описание достижения 2", 30,100)
        )
    }


}