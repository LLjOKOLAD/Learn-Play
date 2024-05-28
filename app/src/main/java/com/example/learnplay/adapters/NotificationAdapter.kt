package com.example.learnplay.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnplay.R
import com.example.learnplay.dataClasses.NotificationObject

class NotificationAdapter(
    private val notificationList: List<NotificationObject>,
    private val onNotificationClick: (NotificationObject) -> Unit
) : RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false)
        return NotificationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = notificationList[position]
        holder.labelTextView.text = notification.label
        holder.messageTextView.text = notification.message
        holder.dateTextView.text = notification.dateNote
        holder.itemView.setOnClickListener { onNotificationClick(notification) }
    }

    override fun getItemCount() = notificationList.size

    class NotificationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labelTextView: TextView = itemView.findViewById(R.id.labelTextView)
        val messageTextView: TextView = itemView.findViewById(R.id.messageTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
    }
}