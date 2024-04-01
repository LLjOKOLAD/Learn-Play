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
        val userPassRep: EditText = findViewById(R.id.user_pass_rep)
        val button : Button = findViewById(R.id.button_reg)
        val linkToAuth : TextView = findViewById(R.id.link_to_auth)
        val resButton : Button = findViewById(R.id.button_reset)

        val db = DbHelper(this,null)
        val user : User? = db.getLogUser()
        db.close()
        if(user != null){
            val intent = Intent(this, MainProfile::class.java)
            startActivity(intent)
        }

        resButton.setOnClickListener {
            val resDb = DbHelper(this, null)
            if (resDb.deleteDatabase(this)){
                Toast.makeText(this,"Database cleared",Toast.LENGTH_SHORT).show()
            }
            resDb.close()
        }


        linkToAuth.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val pass = userPass.text.toString().trim()
            val passRep = userPassRep.text.toString().trim()

            if(login ==  "" || email == ""||pass == "" || passRep == "")
                Toast.makeText(this,"Не все поля заполнены",Toast.LENGTH_SHORT).show()
            else {
                if (pass == passRep) {
                    val user1 = User(login, email, pass, "","True",1f,0,"")

                    val db1 = DbHelper(this, null)
                    db1.addUser(user1)
                    Toast.makeText(this, "Пользователь $email добавлен", Toast.LENGTH_LONG).show()

                    userLogin.text.clear()
                    userEmail.text.clear()
                    userPass.text.clear()
                    userPassRep.text.clear()

                    db1.close()

                    val intent = Intent(this, MainProfile::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this,"Пароли не совпадают",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}