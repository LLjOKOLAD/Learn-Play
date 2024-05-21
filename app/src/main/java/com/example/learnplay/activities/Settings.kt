package com.example.learnplay.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.learnplay.DbHelper
import com.example.learnplay.R
import com.example.learnplay.TasksChallenging
import com.example.learnplay.dataClasses.Question
import com.zanvent.mathview.MathView

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val db = DbHelper(this,null)

        val user = db.getLogUser()

        if (user != null) {
            val button: Button = findViewById(R.id.ch_button)
            button.setOnClickListener {
                Toast.makeText(
                    this,
                    "Login: ${user.login}\nEmail: ${user.email}\nPass: ${user.pass}",
                    Toast.LENGTH_LONG
                ).show()
            }

            val lgOutButton: Button = findViewById(R.id.lg_out_button)

            lgOutButton.setOnClickListener {

                db.LogUser(user.email, "False")
                db.close()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            val addBut: Button = findViewById(R.id.add50exp)

            addBut.setOnClickListener {
                var exp = user.experience
                exp += 50
                user.experience = exp
                db.updateUser(user)
            }

            val takeBut: Button = findViewById(R.id.take50exp)

            takeBut.setOnClickListener {
                var exp = user.experience
                if (exp < 50) {
                    exp = 0
                } else {
                    exp -= 50
                }
                user.experience = exp
                db.updateUser(user)
            }

            val stButton: Button = findViewById(R.id.startTasks)

            stButton.setOnClickListener {
                val questions = listOf(
                    Question("Выберите один правильный ответ.", listOf("Ответ 11342", "Ответ 21324", "Ответ"), "Ответ 11342"),
                    Question("Вопрос 2", listOf("Ответ 1", "Ответ 2", "Ответ 3"), "Ответ 2"),
                    Question("Вопрос 3", listOf(), "3")
                    // Добавьте остальные вопросы сюда
                )

                startTasksChallengingActivity(questions)
            }

            val pgButton: Button = findViewById(R.id.startPing)

            pgButton.setOnClickListener {
                val intent = Intent(this, ServerPinging::class.java)
                startActivity(intent)
            }




            val mathview: MathView = findViewById(R.id.mathView1)
            mathview.text = "<p>Найдите значение выражения: \$2,7/(1- 4/13)\$</p>"
            mathview.pixelScaleType = MathView.Scale.SCALE_DP
            mathview.setTextSize(16)
            mathview.textColor = "#111111"




        }
        db.close()
    }

    fun Context.startTasksChallengingActivity(questions: List<Question>) {
        val intent = Intent(this, TasksChallenging::class.java)
        intent.putParcelableArrayListExtra("questions", ArrayList(questions))
        startActivity(intent)
    }
}