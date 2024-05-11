package com.example.learnplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import android.content.SharedPreferences
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment

class MainProfile : AppCompatActivity() {

    val handler = Handler()

    private val runnable = object : Runnable {
        override fun run() {
            // Ваш код, который нужно выполнять периодически
            Log.d("MainProfile","Repeat")
            handler.postDelayed(this, INTERVAL_MILLISECONDS)
        }
    }

    fun startRepeatingTask() {
        handler.post(runnable)
    }

    fun stopRepeatingTask() {
        handler.removeCallbacks(runnable)
    }

    companion object {
        private const val INTERVAL_MILLISECONDS: Long = 5000
    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main_profile)



            val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
            val navController = findNavController(R.id.nav_host_fragment)

        navView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.learningFg -> {
                    // Навигация к фрагменту настроек
                    navController.navigate(R.id.learningFg)
                    true
                }

                R.id.ratingFg ->{
                    navController.navigate(R.id.ratingFg)
                    true
                }

                R.id.tasksFg -> {
                    // Навигация к фрагменту настроек
                    navController.navigate(R.id.tasksFg)
                    true
                }

                R.id.profileFg -> {
                    // Навигация к фрагменту профиля
                    navController.navigate(R.id.profileFg)
                    true
                }

                R.id.settingsFg -> {
                    // Навигация к фрагменту настроек
                    navController.navigate(R.id.settingsFg)
                    true
                }



                else -> false
            }
        }












    }
}