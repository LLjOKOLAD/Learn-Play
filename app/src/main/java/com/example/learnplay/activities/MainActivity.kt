package com.example.learnplay.activities

import android.annotation.SuppressLint
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
import com.example.learnplay.dataClasses.User
import org.json.JSONObject
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL


class MainActivity : AppCompatActivity(){

    private var name1 = ""
    private var email1 = ""
    private var pass1 = ""
    var result1 = ""

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
            val au = AuthActivity()
            au.authUser(user.email,user.pass,false)
            val intent = Intent(this, MainProfile::class.java)
            startActivity(intent)
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

            name1 = login
            email1 = email
            pass1 = pass




            if(login ==  "" || email == ""||pass == "" || passRep == "")
                Toast.makeText(this,"Не все поля заполнены",Toast.LENGTH_SHORT).show()
            else {
                if (pass == passRep) {
                    sendPost(login,email,pass)
                }
                else{
                    Toast.makeText(this,"Пароли не совпадают",Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun sendPost(name:String, email:String, pass:String) {
        val userData = JSONObject()
        userData.put("name", name)
        userData.put("email", email)
        userData.put("password", pass)
        FetchDataTask(this).execute(userData.toString())
    }

    class FetchDataTask(private val activity: MainActivity) : AsyncTask<String, Void, String>() {

        private val TIMEOUT_MS = 5000
        private val str = activity.getString(R.string.ip_address)
        private val API_URL = str.toString() + "/registration/addUser"

        override fun doInBackground(vararg params: String): String {
            var result = ""
            try {
                val jsonData = params[0]

                val url = URL(API_URL)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.connectTimeout = TIMEOUT_MS

                connection.doOutput = true
                connection.setRequestProperty("Content-Type", "application/json")

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
            if (result != null) {
                val result1 = result.toString()
                Log.d("Server response", result1)
                if (result1 == "201") {
                    Log.d("Server", "Vse rabotaet")
                    activity.succEnter(true)
                }
                else if (result1.startsWith("Error")){
                    activity.connError()
                }
                else {
                    Log.d("Server","Cheto ne tak")
                    activity.succEnter(false)
                }
            }
        }
    }

    fun connError(){
        Toast.makeText(this,"Ошибка соединения с сервером",Toast.LENGTH_SHORT).show()
    }

    fun succEnter(yes:Boolean){
        if(yes){
            Toast.makeText(this,"Регистрация успешна!", Toast.LENGTH_SHORT).show()
            val db = DbHelper(this, null)
            db.addNewUser(name1, email1, pass1)
            Log.d("Register",db.getUser(email1,pass1).toString())
            db.close()
            val au = AuthActivity()
            au.authUser(email1,pass1,false)
            val intent = Intent(this, MainProfile::class.java)
            startActivity(intent)

        }
        else{
            Toast.makeText(this,"Пользователь с таким email уже существует!",Toast.LENGTH_SHORT).show()
        }
    }
}