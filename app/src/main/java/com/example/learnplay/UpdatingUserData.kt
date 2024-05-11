package com.example.learnplay

import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class UpdatingUserData {

    class FetchDataTask() : AsyncTask<Void, Void, String>() {

        private val TIMEOUT_MS = 5000
        override fun doInBackground(vararg params: Void?): String {
            var result = ""
            try {

                val url = URL("http://localhost:8080/profile/info")
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
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
                val remoteUser = Gson().fromJson(result, RemoteUser::class.java)
                Log.d("UpdatingUser", remoteUser.toString())
            } else {
                Log.d("UpdatingUser","Server error")
            }
        }

    }
}

data class RemoteUser(
    @SerializedName("userName")
    val userName: String,
    @SerializedName("exp")
    val exp: Int,
    @SerializedName("multiplier")
    val multiplier: Double,
    @SerializedName("rankPlace")
    val rankPlace: Int,
    @SerializedName("achievement")
    val achievement: List<List<Any>>
)