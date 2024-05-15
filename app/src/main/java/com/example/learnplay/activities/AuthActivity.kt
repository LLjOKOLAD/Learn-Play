package com.example.learnplay.activities

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.learnplay.DbHelper
import com.example.learnplay.R
import java.io.DataOutputStream
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL

class AuthActivity : AppCompatActivity() {

    private var notif:Boolean = false
    private var email1: String = ""
    private var pass1: String = ""
    fun authUser(email:String, pass:String, noti:Boolean){
        notif = noti
        sendPost(email,pass)
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

            email1 = email

            if(email == ""|| pass == "" )
                Toast.makeText(this,"Не все поля заполнены", Toast.LENGTH_SHORT).show()
            else {
                authUser(email,pass,true)
            }
        }
    }

    fun sendPost(email:String, pass:String) {
        val userData = "username=${email}&password=${pass}"
        FetchDataTask(this).execute(userData)
    }

    class FetchDataTask(private val activity: AuthActivity) : AsyncTask<String, Void, String>() {

        private val activityReference = WeakReference(activity)

        private val str = activity.getString(R.string.ip_address)

        private val TIMEOUT_MS = 5000
        private val API_URL = str.toString() + "/entry"

        override fun doInBackground(vararg params: String): String {
            var result = ""
            try {
                val jsonData = params[0]

                val url = URL(API_URL)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.connectTimeout = TIMEOUT_MS

                connection.doOutput = true
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

                val outputStream = DataOutputStream(connection.outputStream)
                outputStream.write(jsonData.toByteArray(Charsets.UTF_8))
                outputStream.flush()
                outputStream.close()

                val responseCode = connection.responseCode


                result = "$responseCode"

            } catch (e: Exception) {
                e.printStackTrace()
                result = "Error: ${e.message}"
            }
            return result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            val activity = activityReference.get()
            if (activity != null) {
                if (result != null) {
                    val result1 = result.toString()
                    Log.d("Server response auth", result1)
                    if (result1 == "200") {
                        Log.d("Server auth", "Vse rabotaet")
                        activity.authSucc(true)

                    } else {
                        Log.d("Server auth", "Cheto ne tak")
                        activity.authSucc(false)
                    }
                }
            }
        }
    }




    fun authSucc(yes:Boolean){
        if(yes){
            if(notif){
                Toast.makeText(this,"Вход успешен!", Toast.LENGTH_SHORT).show()
                val db = DbHelper(this,null)
                Log.d("Authentication", db.getUser(email1,pass1).toString())
                if(!db.getUser(email1,pass1)){
                    db.addUser(email1,pass1)
                }
                else{
                    db.LogUser(email1,"True")
                }

                val intent = Intent(this, MainProfile::class.java)
                startActivity(intent)
            }
        }
        else{
            if(notif){
                Toast.makeText(this,"Неверные почта или пароль!",Toast.LENGTH_SHORT).show()
            }
        }
    }

}