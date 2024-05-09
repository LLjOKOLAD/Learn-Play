package com.example.learnplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class AuthActivity : AppCompatActivity(), OnServerResponseListener {
    val sca = ServerConnectionAuth(this)

    fun authUser(email:String, pass:String){
        sca.sendPost(email,pass)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val userEmail: EditText = findViewById(R.id.user_email_auth)
        val userPass: EditText = findViewById(R.id.user_pass_auth)
        val button : Button = findViewById(R.id.button_auth)
        val linkToReg : TextView = findViewById(R.id.link_to_reg)

        linkToReg.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        button.setOnClickListener {
            val email = userEmail.text.toString().trim()
            val pass = userPass.text.toString().trim()

            if(email == ""|| pass == "" )
                Toast.makeText(this,"Не все поля заполнены", Toast.LENGTH_SHORT).show()
            else {
                authUser(email,pass)
            }
        }
    }




    override fun onServerResponseReceived(result: String) {


        Log.d("Server auth response main", result)

        if(result == "200"){
            Toast.makeText(this,"Вход успешен!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainProfile::class.java)
            startActivity(intent)
        }
        else{
            Toast.makeText(this,"Неверные почта или пароль!",Toast.LENGTH_SHORT).show()
        }
    }
}