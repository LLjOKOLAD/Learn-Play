package com.example.learnplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.content.SharedPreferences

class MainProfile : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_profile)

        val db = DbHelper(this,null)



        val user : User? = db.getLogUser()

        if (user != null) {
            val button: Button = findViewById(R.id.ch_button)

            button.setOnClickListener {
                Toast.makeText(
                    this,
                    "Login: ${user.login}\nEmail: ${user.email}\nPass: ${user.pass}",
                    Toast.LENGTH_LONG
                ).show()
            }

            val lgOutButton : Button = findViewById(R.id.lg_out_button)

            lgOutButton.setOnClickListener {
                val db = DbHelper(this,null)
                db.LogUser(user.login,"False")
                db.close()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }

            



        }





    }
}