package com.example.learnplay

import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import com.example.learnplay.dataClasses.Task
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.Retrofit
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ServerPost {




    class FetchDataTask(private val textView: TextView, private val ip_ad: String) : AsyncTask<Void, Void, String>() {

        private val TIMEOUT_MS = 5000
        override fun doInBackground(vararg params: Void?): String {
            var result = ""
            try {

                val url = URL(ip_ad)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "POST"
                connection.connectTimeout = TIMEOUT_MS
                val responseCode = connection.responseCode
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val reader = BufferedReader(InputStreamReader(connection.inputStream))
                    val response = StringBuilder()
                    var inputLine: String?
                    while (reader.readLine().also { inputLine = it } != null) {
                        response.append(inputLine)
                    }
                    reader.close()
                    result = response.toString()
                } else {
                    result = "Error: $responseCode"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                result = "Error: ${e.message}"
            }
            return result
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            var allNorm: Boolean = true;
            if (result != null) {
                val result1 = result.toString()
                allNorm = result1.startsWith("Error")
                Log.d("ServPing", allNorm.toString())
            }
            if (!allNorm) {
                val task = Gson().fromJson(result, Task::class.java)
                val stringBuilder = StringBuilder()
                stringBuilder.append("idTask: ${task.idTask}\n")
                stringBuilder.append("idTopic: ${task.idTopic}\n")
                stringBuilder.append("quotation: ${task.quotation}\n")
                stringBuilder.append("addText: ${task.addText}\n")
                stringBuilder.append("answer: ${task.answer}\n")
                stringBuilder.append("exp: ${task.exp}\n")
                stringBuilder.append("image: ${task.image}\n")

                textView.text = stringBuilder.toString()
            } else {
                textView.text = "Error: The server is not responding"
            }
        }

    }
}