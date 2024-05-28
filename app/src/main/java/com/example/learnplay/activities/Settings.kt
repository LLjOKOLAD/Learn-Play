package com.example.learnplay.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.learnplay.ApiClient
import com.example.learnplay.DbHelper
import com.example.learnplay.R
import com.example.learnplay.dataClasses.User
import com.zanvent.mathview.MathView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Settings : AppCompatActivity() {

    private lateinit var db: DbHelper

    private lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)


        db = DbHelper(this,null)

        user = db.getLogUser()!!


        if (user != null) {

            val chEmailText:EditText = findViewById(R.id.chEmailText)
            val chPassOld:EditText = findViewById(R.id.chPassOld)
            val chPassNew:EditText = findViewById(R.id.chPassNew)
            val chPassNewRep:EditText = findViewById(R.id.chPassNewRep)

            val chEmailButt:Button = findViewById(R.id.chEmailButt)
            val chPassButt: Button = findViewById(R.id.chPassButt)


            val lgOutButton: Button = findViewById(R.id.lg_out_button)

            lgOutButton.setOnClickListener {
                logOut()
            }


            chEmailButt.setOnClickListener(){
                val email = chEmailText.text.toString().trim()
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(this, "Пожалуйста, введите email", Toast.LENGTH_SHORT).show()
                } else if (isValidEmail(email)) {
                    chEmailText.setText("")
                    changeEmail(email)
                } else {
                    Toast.makeText(this, "Email некорректен", Toast.LENGTH_SHORT).show()
                }
            }

            chPassButt.setOnClickListener(){
                val oldpass = chPassOld.text.toString().trim()
                if(TextUtils.isEmpty(oldpass)){
                    Toast.makeText(this, "Пожалуйста, введите старый пароль", Toast.LENGTH_SHORT).show()
                }
                else if(user.pass != oldpass){
                    Toast.makeText(this, "Старый пароль неверный", Toast.LENGTH_SHORT).show()
                } else{
                    val newpass = chPassNew.text.toString().trim()
                    val newpassrep = chPassNewRep.text.toString().trim()
                    when{
                        newpass.length < 8 ->{
                            Toast.makeText(this, "Минимальная длина пароля 8", Toast.LENGTH_SHORT).show()
                        }
                        newpass.contains(" ") ->{
                            Toast.makeText(this, "В пароле не должно содержаться пробелов", Toast.LENGTH_SHORT).show()
                        }
                        newpass != newpassrep ->{
                            Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT).show()
                        }
                        else ->{
                            chPassOld.setText("")
                            chPassNew.setText("")
                            chPassNewRep.setText("")
                            changePassword(oldpass,newpass)
                        }
                    }

                }
            }

        }
        db.close()
    }

    private fun changeEmail(newEmail: String) {
        val call = ApiClient.apiService.changeEmail(newEmail)
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result == true) {
                        Toast.makeText(this@Settings, "Email успешно изменен", Toast.LENGTH_SHORT).show()
                        if(user!=null) {
                            user.email = newEmail
                            db.updateUser(user)
                        }
                    } else {
                        Toast.makeText(this@Settings, "Не удалось изменить Email", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@Settings, "Ошибка сервера: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@Settings, "Ошибка сети: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun changePassword(oldPassword: String, newPassword: String) {
        val call = ApiClient.apiService.changePassword(oldPassword, newPassword)
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result == true) {
                        Toast.makeText(this@Settings, "Пароль успешно изменен", Toast.LENGTH_SHORT).show()
                        if(user != null){
                            user.pass = newPassword
                            db.updateUser(user)
                        }
                    } else {
                        Toast.makeText(this@Settings, "Не удалось изменить пароль", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@Settings, "Ошибка сервера: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@Settings, "Ошибка сети: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun logOut(){
        db.LogUser(user.email, "False")
        db.close()

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun isValidEmail(email: CharSequence?): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}