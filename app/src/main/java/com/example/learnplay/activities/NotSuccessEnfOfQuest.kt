package com.example.learnplay.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.learnplay.R

class NotSuccessEnfOfQuest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_not_success_enf_of_quest)

        val nextNotSuccBut: Button = findViewById(R.id.nextNotSuccBut)

        nextNotSuccBut.setOnClickListener(){
            val intent = Intent(this,MainProfile::class.java)
            startActivity(intent)
        }

    }
}