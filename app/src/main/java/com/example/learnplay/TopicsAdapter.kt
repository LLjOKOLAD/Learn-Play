package com.example.learnplay

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class TopicsAdapter(context: Context, private val topicsList: List<Topic>) :
    ArrayAdapter<Topic>(context, 0, topicsList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                R.layout.topic_list_item, parent, false
            )
        }

        val currentTopic = getItem(position)

        val topicNameTextView = listItemView!!.findViewById<TextView>(R.id.topicNameTextView)
        val taskCounterTextView = listItemView.findViewById<TextView>(R.id.taskCounterTextView)
        val decreaseButton = listItemView.findViewById<Button>(R.id.decreaseButton)
        val increaseButton = listItemView.findViewById<Button>(R.id.increaseButton)

        topicNameTextView.text = currentTopic?.name
        taskCounterTextView.text = currentTopic?.taskCount.toString()

        decreaseButton.setOnClickListener {
            currentTopic?.decreaseTaskCount()
            taskCounterTextView.text = currentTopic?.taskCount.toString()
        }

        increaseButton.setOnClickListener {
            currentTopic?.increaseTaskCount()
            taskCounterTextView.text = currentTopic?.taskCount.toString()
        }

        return listItemView
    }
}
