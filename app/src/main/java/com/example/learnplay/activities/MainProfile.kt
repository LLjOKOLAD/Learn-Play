package com.example.learnplay.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import android.os.Handler
import android.util.Log
import com.example.learnplay.R

class MainProfile : AppCompatActivity() {


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

                R.id.ratingFg -> {
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

                R.id.manualFg -> {
                    // Навигация к фрагменту профиля
                    navController.navigate(R.id.manualFg)
                    true
                }


                else -> false
            }
        }

        val fragmentToOpen = intent.getStringExtra("fragment_to_open")
        if (fragmentToOpen != null) {
            when (fragmentToOpen) {
                "learningFg" -> navController.navigate(R.id.learningFg)
                "ratingFg" -> navController.navigate(R.id.ratingFg)
                "tasksFg" -> navController.navigate(R.id.tasksFg)
                "profileFg" -> navController.navigate(R.id.profileFg)
                "manualFg" -> navController.navigate(R.id.manualFg)
            }
        }

    }
}