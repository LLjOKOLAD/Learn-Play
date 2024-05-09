package com.example.learnplay

import android.os.AsyncTask
import android.util.Log
import org.json.JSONObject
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL



class ServerConnectionAuth(private val listener: OnServerResponseListener) {

    fun sendPost(email:String, pass:String) {
        val userData = "username=${email}&password=${pass}"
        FetchDataTask(listener).execute(userData)
    }

    class FetchDataTask(private val listener: OnServerResponseListener) : AsyncTask<String, Void, String>() {

        private val TIMEOUT_MS = 5000
        private val API_URL = "http://192.168.0.101:8080/entry"

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
            var allNorm: Boolean = true
            if (result != null) {
                val result1 = result.toString()
                Log.d("Server response auth", result1)
                allNorm = result1.startsWith("Error")
                Log.d("ServPing auth", allNorm.toString())
                if (result1 == "200") {
                    Log.d("Server auth", "Vse rabotaet")

                } else {
                    Log.d("Server auth","Cheto ne tak")

                }
            }
            listener.onServerResponseReceived(result ?: "")

        }

    }
}