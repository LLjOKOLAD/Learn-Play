package com.example.learnplay

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast


class MainActivity : AppCompatActivity(), OnServerResponseListener {

    var email1 = ""
    var pass1 = ""
    val sca = ServerConnectionAuth(this)
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



        val db = DbHelper(this,null)

        val user : User? = db.getLogUser()
        db.close()
        if(user != null){
            val intent = Intent(this, MainProfile::class.java)
            startActivity(intent)
        }




        linkToAuth.setOnClickListener {
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }


        val sc = ServerConnection(this)





        button.setOnClickListener {
            val login = userLogin.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val pass = userPass.text.toString().trim()
            val passRep = userPassRep.text.toString().trim()

            email1 = email
            pass1 = pass




            if(login ==  "" || email == ""||pass == "" || passRep == "")
                Toast.makeText(this,"Не все поля заполнены",Toast.LENGTH_SHORT).show()
            else {
                if (pass == passRep) {
                    sc.sendPost(login,email,pass)
                }
                else{
                    Toast.makeText(this,"Пароли не совпадают",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    override fun onServerResponseReceived(result: String) {
        val auth = AuthActivity()

        Log.d("Server response main", result)

        if(result == "201"){
            Toast.makeText(this,"Регистрация успешна!", Toast.LENGTH_SHORT).show()
            sca.sendPost(email1,pass1)
            val intent = Intent(this, MainProfile::class.java)
            startActivity(intent)
        }
        else{
            Toast.makeText(this,"Пользователь с таким email уже существует",Toast.LENGTH_SHORT).show()
        }
    }


}