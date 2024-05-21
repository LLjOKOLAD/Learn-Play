package com.example.learnplay.activities

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.example.learnplay.DbHelper
import com.example.learnplay.R
import java.io.DataOutputStream
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL

object AuthHelper {
    private const val TIMEOUT_MS = 5000

    fun sendPost(context: Context, email: String, pass: String, notif: Boolean) {
        val userData = "username=${email}&password=${pass}"
        Log.d("Logging",userData)
        FetchDataTask(context, email, pass, notif).execute(userData)
    }

    private class FetchDataTask(context: Context, private val email: String, private val pass: String, private val notif: Boolean) : AsyncTask<String, Void, String>() {

        private val contextReference = WeakReference(context)

        override fun doInBackground(vararg params: String): String {
            var result = ""
            try {
                val jsonData = params[0]
                val context = contextReference.get() ?: return "Error: Context is null"
                val ipAddress = context.getString(R.string.ip_address)
                val API_URL = "$ipAddress/entry"

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
            val context = contextReference.get()
            if (context != null) {
                if (result != null) {
                    val result1 = result.toString()
                    Log.d("Server response auth", result1)
                    if (result1 == "200") {
                        Log.d("Server auth", "Vse rabotaet")
                        authSucc(context, true)
                    } else {
                        Log.d("Server auth", "Cheto ne tak")
                        authSucc(context, false)
                    }
                }
            }
        }

        private fun authSucc(context: Context, yes: Boolean) {
            if (yes) {
                if (notif) {
                    Toast.makeText(context, "Вход успешен!", Toast.LENGTH_SHORT).show()
                    val db = DbHelper(context, null)
                    Log.d("Authentication", db.getUser(email, pass).toString())
                    if (!db.getUser(email, pass)) {
                        db.addUser(email, pass)
                    } else {
                        db.LogUser(email, "True")
                    }
                    val user = db.getLogUser()


                    db.close()
                    val intent = Intent(context, MainProfile::class.java)
                    context.startActivity(intent)
                }
            } else {
                if (notif) {
                    Toast.makeText(context, "Неверные почта или пароль!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


