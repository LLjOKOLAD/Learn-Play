package com.example.learnplay.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.example.learnplay.R
import com.example.learnplay.dataClasses.Topic

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
        val decreaseButton = listItemView.findViewById<ImageButton>(R.id.decreaseButton)
        val increaseButton = listItemView.findViewById<ImageButton>(R.id.increaseButton)

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
