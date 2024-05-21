package com.example.learnplay

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.learnplay.dataClasses.Question

class TasksChallenging : AppCompatActivity() {

    private lateinit var textNameQuestion: TextView
    private lateinit var textViewQuestion: TextView
    private lateinit var radioGroupAnswers: RadioGroup
    private lateinit var editTextAnswer: EditText
    private lateinit var buttonConfirm: Button
    private lateinit var textViewResult: TextView
    private lateinit var buttonNext: Button
    private lateinit var questions: List<Question>

    private var currentQuestionIndex = 0




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_challenging)

        questions = intent.getParcelableArrayListExtra<Question>("questions") ?: listOf()


        textNameQuestion = findViewById(R.id.textNameQuestion)
        textViewQuestion = findViewById(R.id.textViewQuestion)
        radioGroupAnswers = findViewById(R.id.radioGroupAnswers)
        editTextAnswer = findViewById(R.id.editTextAnswer)
        buttonConfirm = findViewById(R.id.buttonConfirm)
        textViewResult = findViewById(R.id.textViewResult)
        buttonNext = findViewById(R.id.buttonNext)

        updateQuestion()

        buttonConfirm.setOnClickListener {
            checkAnswer()
        }

        buttonNext.setOnClickListener {
            showNextQuestion()
        }
    }

    private fun updateQuestion() {
        val question = questions[currentQuestionIndex]
        textViewQuestion.text = question.text
        radioGroupAnswers.visibility = if (question.options.isNotEmpty()) View.VISIBLE else View.GONE
        editTextAnswer.visibility = if (question.options.isEmpty()) View.VISIBLE else View.GONE
        buttonConfirm.visibility = View.VISIBLE
        textViewResult.visibility = View.GONE
        buttonNext.visibility = View.GONE

        if (question.options.isNotEmpty()) {
            radioGroupAnswers.removeAllViews()
            for ((index, option) in question.options.withIndex()) {
                val radioButton = RadioButton(this)
                radioButton.id = index
                radioButton.text = option
                radioGroupAnswers.addView(radioButton)
            }
        }
    }

    private fun checkAnswer() {
        val question = questions[currentQuestionIndex]
        var userAnswer: String
        if (question.options.isEmpty()){
            userAnswer = editTextAnswer.text.toString()
        }
        else{
            val selectedAnswerIndex = radioGroupAnswers.checkedRadioButtonId
            userAnswer = questions[currentQuestionIndex].options[selectedAnswerIndex]
        }


        val result: String = if (userAnswer == question.answer) {
            textViewResult.setTextColor(Color.parseColor("#31FA11"))
            "Правильно!"
        } else {
            textViewResult.setTextColor(Color.parseColor("#EE0606"))
            "Неправильно!"
        }

        textViewResult.text = result
        textViewResult.visibility = View.VISIBLE

        buttonConfirm.visibility = View.GONE
        buttonNext.visibility = View.VISIBLE
    }

    private fun showNextQuestion() {
        currentQuestionIndex++
        if (currentQuestionIndex < questions.size) {
            updateQuestion()
        } else {
            // Пользователь ответил на все вопросы, выполните соответствующие действия, например, завершите активность
            finish()
        }
    }
}

