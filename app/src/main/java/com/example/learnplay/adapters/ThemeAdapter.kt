package com.example.learnplay.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import com.example.learnplay.manual.ManualPage1
import com.example.learnplay.R
import com.example.learnplay.manual.ManualPage2
import com.example.learnplay.manual.ManualPage3

class ThemeAdapter(context: Context, private val themes: List<String>) :
    ArrayAdapter<String>(context, R.layout.list_item_layout, themes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false)
        }

        val currentTheme = themes[position]

        val themeNameTextView = listItemView!!.findViewById<TextView>(R.id.themeNameTextView)
        themeNameTextView.text = currentTheme

        val gotoButton = listItemView.findViewById<Button>(R.id.gotoButton)
        gotoButton.setOnClickListener {
            Log.d("Manual","Theme $position")
            val context = listItemView.context

            // Создайте Intent для вызова соответствующей активности
            val intent = when (currentTheme) {
                "Вероятность и статистика" -> Intent(context, ManualPage1::class.java)
                "Алгебраические выражения. Степени и корни" -> Intent(context, ManualPage2::class.java)
                "Theme 3" -> Intent(context, ManualPage3::class.java)
                else -> null
            }

            intent?.let {
                context.startActivity(it)
            }
        }

        return listItemView
    }
}