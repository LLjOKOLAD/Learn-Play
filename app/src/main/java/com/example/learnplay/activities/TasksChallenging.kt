package com.example.learnplay.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.caverock.androidsvg.SVG
import com.example.learnplay.ApiClient
import com.example.learnplay.R
import com.example.learnplay.dataClasses.TaskResponse
import com.zanvent.mathview.MathView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.io.InputStream

class TasksChallenging : AppCompatActivity() {

    private lateinit var textNameQuestion: TextView
    private lateinit var textViewQuestion: MathView
    private lateinit var taskImageView: ImageView
    private lateinit var editTextAnswer: EditText
    private lateinit var buttonConfirm: Button
    private lateinit var textViewResult: TextView
    private lateinit var buttonNext: Button

    private var curTaskNum: Int = 0


    private var currentQuestion: TaskResponse? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks_challenging)



        textNameQuestion = findViewById(R.id.textNameQuestion)
        textViewQuestion = findViewById(R.id.textViewQuestion)
        taskImageView = findViewById(R.id.taskImage)
        editTextAnswer = findViewById(R.id.editTextAnswer)
        buttonConfirm = findViewById(R.id.buttonConfirm)
        textViewResult = findViewById(R.id.textViewResult)
        buttonNext = findViewById(R.id.buttonNext)


        textViewQuestion.setTextSize(15)
        textViewQuestion.textColor = "#FFFFFF"
        textViewQuestion.setTextAlign("left")

        checkEndQuest()

        buttonConfirm.setOnClickListener {
            checkAnswer()
        }

        buttonNext.setOnClickListener {
            checkEndQuest()
        }
    }

    private fun checkEndQuest() {
        val call = ApiClient.apiService.isEndQuest()

        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    val isEnd = response.body() ?: false
                    if (isEnd) {
                        // Проверка успешности квеста
                        checkQuestSuccess()
                    } else {
                        getNewTask()
                    }
                } else {
                    Toast.makeText(this@TasksChallenging, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@TasksChallenging, "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun checkQuestSuccess() {
        val call = ApiClient.apiService.successQuest()

        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    val isSuccess = response.body() ?: false
                    if (isSuccess) {
                        goNext(true)
                    } else {
                        goNext(false)
                    }

                } else {
                    Toast.makeText(this@TasksChallenging, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@TasksChallenging, "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun goNext(yes:Boolean){
        if (yes){
            val intent = Intent(this,SuccessEnfOfQuest::class.java)
            startActivity(intent)
        }
        else{
            val intent = Intent(this,NotSuccessEnfOfQuest::class.java)
            startActivity(intent)
        }

    }

    private fun getNewTask() {
        val call = ApiClient.apiService.getTask()

        call.enqueue(object : Callback<TaskResponse> {
            override fun onResponse(call: Call<TaskResponse>, response: Response<TaskResponse>) {
                if (response.isSuccessful) {
                    val taskResponse = response.body()
                    taskResponse?.let {
                        currentQuestion = it
                        updateQuestion()
                    }
                } else {
                    Toast.makeText(this@TasksChallenging, "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TaskResponse>, t: Throwable) {
                Toast.makeText(this@TasksChallenging, "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateQuestion() {
        currentQuestion?.let { question ->
            textViewQuestion.text = question.quotation
            textViewQuestion.textColor = "#000000"
            editTextAnswer.text.clear()
            buttonConfirm.visibility = View.VISIBLE
            textViewResult.visibility = View.GONE
            buttonNext.visibility = View.GONE
            if(question.image!=null){
                // Извлечение названия файла изображения из пути
                Log.d("Image","${question.image}")
                val imageName = question.image.substringAfterLast("/")
                val fileExtension = imageName.substringAfterLast(".")

                // Загрузка изображения в зависимости от типа файла
                when (fileExtension.lowercase()) {
                    "svg" -> loadSVGFromAssets("images/$imageName", taskImageView, 800,400)
                    "png", "jpg", "jpeg" -> loadImageFromAssets("images/$imageName", taskImageView)
                    else -> {}
                }


            }
        }


        curTaskNum += 1
        textNameQuestion.text = "Задание $curTaskNum"


    }

    private fun loadSVGFromAssets(imagePath: String, imageView: ImageView, width: Int, height: Int) {
        try {
            // Создание белого прямоугольника с нужными размерами
            val backgroundBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            backgroundBitmap.eraseColor(Color.WHITE)

            // Создание Canvas и рисование прямоугольника
            val backgroundCanvas = Canvas(backgroundBitmap)
            val backgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            backgroundCanvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), backgroundPaint)

            // Создание SVG из InputStream
            val inputStream: InputStream = assets.open(imagePath)
            val svg = SVG.getFromInputStream(inputStream)

            // Создание Drawable из SVG
            val drawable = PictureDrawable(svg.renderToPicture())

            // Установка Drawable в ImageView с белым фоном
            imageView.setLayerType(ImageView.LAYER_TYPE_SOFTWARE, null)
            imageView.setImageDrawable(drawable)
            imageView.setBackgroundColor(Color.WHITE)
            imageView.layoutParams.width = width
            imageView.layoutParams.height = height

            // Закрытие InputStream
            inputStream.close()

        } catch (e: IOException) {
            e.printStackTrace()
            // Обработка ошибки (например, показать placeholder)

        }
    }

    private fun loadImageFromAssets(imagePath: String, imageView: ImageView) {
        try {
            // Открытие файла из assets
            val inputStream: InputStream = assets.open(imagePath)

            // Создание Drawable из InputStream
            val drawable = Drawable.createFromStream(inputStream, null)

            // Установка Drawable в ImageView
            imageView.setImageDrawable(drawable)

            // Закрытие InputStream
            inputStream.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun checkAnswer() {
        val question = currentQuestion ?: return
        val userAnswer = editTextAnswer.text.toString()

        val result: String = if (userAnswer.toFloat().toString() == question.answer) {
            sendSuccessTask(question.idTask)
            textViewResult.setTextColor(Color.parseColor("#31FA11"))
            question.answer
        } else {
            sendFailedTask(question.idTask)
            textViewResult.setTextColor(Color.parseColor("#EE0606"))
            question.answer
        }

        textViewResult.text = formatNumber(result.toFloat())
        textViewResult.visibility = View.VISIBLE

        buttonConfirm.visibility = View.GONE
        buttonNext.visibility = View.VISIBLE
    }

    fun formatNumber(number: Float): String {
        return if (number == number.toInt().toFloat()) {
            number.toInt().toString() // Вывод целого числа без дробной части
        } else {
            number.toString() // Вывод с дробной частью
        }
    }

    private fun sendSuccessTask(taskId: Int) {
        val call = ApiClient.apiService.successTask(taskId)
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    // Do something on success
                } else {
                    Toast.makeText(this@TasksChallenging, "Failed to update task success: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@TasksChallenging, "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun sendFailedTask(taskId: Int) {
        val call = ApiClient.apiService.failedTask(taskId)
        call.enqueue(object : Callback<Boolean> {
            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if (response.isSuccessful) {
                    // Do something on success
                } else {
                    Toast.makeText(this@TasksChallenging, "Failed to update task failure: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(this@TasksChallenging, "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }




}

private fun MathView.setTextAlign(align: String) {
    val textAlignScript = "document.getElementsByTagName('body')[0].style.textAlign = '$align';"
    this.evaluateJavascript(textAlignScript, null)
}

