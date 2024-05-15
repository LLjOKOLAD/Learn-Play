// AchievementAdapter.kt
package com.example.learnplay.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnplay.R
import com.example.learnplay.dataClasses.Achievement

class AchievementAdapter(private val achievements: List<Achievement>) :
    RecyclerView.Adapter<AchievementAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.achievement_card_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val achievement = achievements[position]
        holder.textViewTitle.text = achievement.title
        holder.textViewDescription.text = achievement.description
        holder.progressBar.progress = achievement.progress
        holder.progressBar.max = achievement.maxProgress
        val progText: String = achievement.progress.toString() + "/" + achievement.maxProgress.toString()
        holder.progressText.text = progText
    }

    override fun getItemCount(): Int {
        return achievements.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.textViewTitle)
        val textViewDescription: TextView = itemView.findViewById(R.id.textViewDescription)
        val progressBar: ProgressBar = itemView.findViewById(R.id.progressBar)
        val progressText: TextView = itemView.findViewById(R.id.progressText)
    }
}
