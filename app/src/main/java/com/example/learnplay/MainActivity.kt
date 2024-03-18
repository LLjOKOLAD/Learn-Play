package com.example.learnplay

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val userLogin: EditText = findViewById(R.id.user_login)
        val userEmail: EditText = findViewById(R.id.user_email)
        val userPass: EditText = findViewById(R.id.user_pass)
        val button : Button = findViewById(R.id.button_reg)
        val linkToAuth : TextView = findViewById(R.id.link_to_auth)
        val resButton : Button = findViewById(R.id.button_res)

        val db = DbHelper(this,null)
        val user : User? = db.getLogUser()
        if(user != null){
            val intent = Intent(this, MainProfile::class.java)
            startActivity(intent)
        }


        resButton.setOnClickListener {
            Toast.makeText(this,"You clicked delete",Toast.LENGTH_SHORT).show()
            val db = DbHelper(this, null)
            if(db.deleteDatabase(this)){
                Toast.makeText(this,"Database successfully clear",Toast.LENGTH_SHORT).show()
            }
            db.close()
        }

        linkToAuth.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if(login == ""|| email == ""||pass == "" )
                Toast.makeText(this,"Не все поля заполнены",Toast.LENGTH_LONG).show()
            else {
                val user = User(login,email,pass,"True")

                val db =DbHelper(this,null)
                db.addUser(user)
                Toast.makeText(this,"Пользователь $login добавлен",Toast.LENGTH_LONG).show()

                userLogin.text.clear()
                userEmail.text.clear()
                userPass.text.clear()

                val intent = Intent(this, MainProfile::class.java)
                startActivity(intent)
            }
        }

    }
}