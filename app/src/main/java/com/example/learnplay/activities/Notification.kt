package com.example.learnplay.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learnplay.ApiClient
import com.example.learnplay.R
import com.example.learnplay.adapters.NotificationAdapter
import com.example.learnplay.dataClasses.NotificationObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Notification : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificationAdapter
    private lateinit var noNotificationsText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        noNotificationsText = findViewById(R.id.no_notifications_text)

        getNotifications()
    }

    private fun getNotifications() {
        val call = ApiClient.apiService.getListNotification()

        call.enqueue(object : Callback<List<NotificationObject>> {
            override fun onResponse(
                call: Call<List<NotificationObject>>,
                response: Response<List<NotificationObject>>
            ) {
                if (response.isSuccessful) {
                    val notifications = response.body()
                    notifications?.let {
                        if (it.isEmpty()) {
                            noNotificationsText.visibility = View.VISIBLE
                        } else {
                            noNotificationsText.visibility = View.GONE
                            adapter = NotificationAdapter(it) { notification ->
                                onNotificationClicked(notification)
                                launchAct()
                            }
                            recyclerView.adapter = adapter
                        }
                        for (notification in it) {
                            Log.d("Notification", "ID: ${notification.id}, Label: ${notification.label}")
                        }

                    }
                } else {
                    Log.e("Error", "Response code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<NotificationObject>>, t: Throwable) {
                Log.e("Error", "Failed to fetch notifications", t)
            }
        })
    }

    private fun onNotificationClicked(notification: NotificationObject) {
        val call = ApiClient.apiService.viewedNotification(notification.id.toLong())

        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Log.d("Success", "Notification marked as viewed")
                } else {
                    Log.e("Error", "Failed to mark notification as viewed")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Error", "Failed to mark notification as viewed", t)
            }
        })
    }

    private fun launchAct(){
        val intent = Intent(this, MainProfile::class.java)
        intent.putExtra("fragment_to_open", "tasksFg")
        startActivity(intent)
    }


}