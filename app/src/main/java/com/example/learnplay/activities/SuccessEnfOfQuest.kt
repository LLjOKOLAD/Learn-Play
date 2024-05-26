package com.example.learnplay.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.learnplay.R

class SuccessEnfOfQuest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_enf_of_quest)

        val nextSuccBut:Button = findViewById(R.id.nextSuccBut)

        nextSuccBut.setOnClickListener(){
            val intent = Intent(this,MainProfile::class.java)
            startActivity(intent)
        }

    }
}