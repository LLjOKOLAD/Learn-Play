package com.example.learnplay

import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.widget.TextView
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import org.json.JSONObject
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URI
import java.net.URLEncoder

interface OnServerResponseListener {
    fun onServerResponseReceived(result: String)
}


class ServerConnection(private val listener: OnServerResponseListener) {

    fun sendPost(name:String, email:String, pass:String) {
        val userData = JSONObject()
        userData.put("name", name)
        userData.put("email", email)
        userData.put("password", pass)
        FetchDataTask(listener).execute(userData.toString())
    }

    class FetchDataTask(private val listener: OnServerResponseListener) : AsyncTask<String, Void, String>() {

        private val TIMEOUT_MS = 5000
        private val API_URL = "http://192.168.0.101:8080/registration/addUser"

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
            var allNorm: Boolean = true
            if (result != null) {
                val result1 = result.toString()
                Log.d("Server response", result1)
                allNorm = result1.startsWith("Error")
                Log.d("ServPing", allNorm.toString())
                if (result1 == "201") {
                    Log.d("Server", "Vse rabotaet")

                } else {
                    Log.d("Server","Cheto ne tak")

                }
                listener.onServerResponseReceived(result ?: "")
            }

        }

    }
}