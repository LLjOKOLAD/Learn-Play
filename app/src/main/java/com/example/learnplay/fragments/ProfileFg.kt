package com.example.learnplay.fragments

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.learnplay.ApiClient
import com.example.learnplay.dataClasses.Achievement
import com.example.learnplay.adapters.AchievementAdapter
import com.example.learnplay.CharacterSelectionActivity
import com.example.learnplay.DbHelper
import com.example.learnplay.viewModels.ProfileFgViewModel
import com.example.learnplay.R
import com.example.learnplay.activities.Settings
import com.example.learnplay.dataClasses.UserNetwork
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFg : Fragment() {

    private lateinit var textView: TextView
    private lateinit var editText: EditText
    lateinit var recyclerView: RecyclerView
    lateinit var multPerstext: TextView
    lateinit var expAmmtext: TextView
    lateinit var rankPlace: TextView
    private lateinit var iconLige: ImageView


    companion object {
        fun newInstance() = ProfileFg()

    }

    private lateinit var viewModel: ProfileFgViewModel

    private lateinit var userresp: UserNetwork






    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        val db = DbHelper(requireContext(),null)
        val user = db.getLogUser()
        val view = inflater.inflate(R.layout.fragment_profile_fg, container, false)
        recyclerView = view.findViewById(R.id.recyclerViewAchievements)
        rankPlace = view.findViewById(R.id.profTop)
        iconLige = view.findViewById(R.id.ligeIcon)







        if (user != null) {
            Log.d("Profile",user.login)
            Log.d("Profile",user.log_st)
            authenticateUser(user.email,user.pass)
        }
        else{
            Log.d("Profile", "user is empty")
        }


        if (user != null && user.character.isEmpty()) {
            val intent = Intent(requireContext(), CharacterSelectionActivity::class.java)
            startActivity(intent)
        }
        else{
            val userAva: ImageView = view.findViewById(R.id.avatarImageView)
            if (user != null) {
                val resourceName = user.character
                Log.d("Prof","$resourceName")
                val parts = resourceName.split(".")
                val drawableName = parts[parts.size - 1]
                val resId = resources.getIdentifier(drawableName, "drawable", this.toString())
                userAva.setImageResource(resId)
            }
        }

        val settBut: ImageButton = view.findViewById(R.id.enterSetBut)
        settBut.setOnClickListener{
            val intent = Intent(requireContext(), Settings::class.java)
            startActivity(intent)
        }


        //Код для достижений


        recyclerView.layoutManager = LinearLayoutManager(activity)



        // Находим TextView и EditText в макете фрагмента
        textView = view.findViewById(R.id.textView)
        editText = view.findViewById(R.id.editText)

        if (user != null) {
            textView.text = user.name
        }

        val changeNambut: ImageButton = view.findViewById(R.id.changeNameButton)

        // Устанавливаем слушатель кликов на TextView
        textView.setOnClickListener {
            textView.visibility = View.INVISIBLE
            editText.visibility = View.VISIBLE
            editText.setText(textView.text.toString())
            editText.requestFocus()
        }

        // Обработчик события нажатия клавиши "Готово" на клавиатуре
        changeNambut.setOnClickListener {
            // Получаем текст из EditText
            val newText = editText.text.toString()
            // Устанавливаем новый текст в TextView
            textView.text = newText

            changeName(newText)



            // Показываем TextView и скрываем EditText
            textView.visibility = View.VISIBLE
            editText.visibility = View.INVISIBLE

            user?.name = newText
            if (user != null) {
                db.updateUser(user)
            }

        }



        multPerstext = view.findViewById(R.id.multPers)
        expAmmtext = view.findViewById(R.id.expAmm)

        if (user != null) {
            multPerstext.setText(String.format("%.0f%%", user.multiplier * 100))
            expAmmtext.setText(user.experience.toString())
        }

        val avaCh: ImageButton = view.findViewById(R.id.changeAvaButton)

        avaCh.setOnClickListener {
            val intent = Intent(requireContext(), CharacterSelectionActivity::class.java)
            startActivity(intent)
        }


        return view
    }

    private fun authenticateUser(username: String, password: String) {
        val call = ApiClient.apiService.login(username, password)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    //Toast.makeText(requireContext(), "Аутентификация успешна!", Toast.LENGTH_SHORT).show()
                    fetchUserData()
                } else {
                    Toast.makeText(requireContext(), "Ошибка аутентификации: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(requireContext(), "Ошибка: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchUserData() {
        val call = ApiClient.apiService.fetchData()
        call.enqueue(object : Callback<UserNetwork> {
            override fun onResponse(call: Call<UserNetwork>, response: Response<UserNetwork>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    responseData?.let {
                        displayUserData(it,getAchievements(it.achievement))
                    }
                } else {
                    Toast.makeText(requireContext(), "Ошибка получения данных: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserNetwork>, t: Throwable) {
                Toast.makeText(requireContext(), "Ошибка: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.d("Resp","Ошибка: ${t.message}")
            }
        })
    }

    private fun displayUserData(userResponse: UserNetwork, achievements: List<Achievement>) {
        //Toast.makeText(requireContext(), "Пользователь: ${userResponse.userName} \n ", Toast.LENGTH_SHORT).show()
        val db = DbHelper(requireContext(),null)
        val user = db.getLogUser()
        if (user != null) {
            user.name = userResponse.userName
            user.experience = userResponse.exp
            user.multiplier = userResponse.multiplier
            db.updateUser(user)
        }
        db.close()
        textView.setText(userResponse.userName)
        multPerstext.setText(String.format("%.0f%%", userResponse.multiplier * 100))
        expAmmtext.setText(userResponse.exp.toString())
        rankPlace.setText(userResponse.rankPlace.toString())
        recyclerView.adapter = AchievementAdapter(achievements)
        //Toast.makeText(requireContext(),"Данные обновлены",Toast.LENGTH_LONG).show()

        val exp = userResponse.exp

        if (exp in 0 .. 99){
            iconLige.setImageResource(R.drawable.lige_1)
        }
        else if (exp in 100..199){
            iconLige.setImageResource(R.drawable.lige_2)
        }
        else if (exp in 200 .. 299){
            iconLige.setImageResource(R.drawable.lige_3)
        }
        else if (exp in 300 .. 399){
            iconLige.setImageResource(R.drawable.lige_4)
        }
        else if (exp in 400 .. 499){
            iconLige.setImageResource(R.drawable.lige_5)
        }
        else if (exp in 500 .. 599){
            iconLige.setImageResource(R.drawable.lige_6)
        }
        else if (exp >= 600){
            iconLige.setImageResource(R.drawable.lige_7)
        }
    }


    private fun getAchievements(achievementData: List<List<Any>>): List<Achievement> {
        val achievements = mutableListOf<Achievement>()
        for (item in achievementData) {
            if (item.size >= 3) {
                val name = item[0].toString()
                val progress = item[2].toString().toFloat().toInt()
                val maxProgress = item[1].toString().toFloat().toInt()
                achievements.add(Achievement(name, "", progress, maxProgress))
            }
        }
        return achievements.sortedByDescending { it.progress.toFloat() / it.maxProgress }
    }

    private fun changeName(newName: String) {
        val call = ApiClient.apiService.changeName(newName)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Имя успешно изменено", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Ошибка сервера: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(requireContext(), "Ошибка сети: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }










}