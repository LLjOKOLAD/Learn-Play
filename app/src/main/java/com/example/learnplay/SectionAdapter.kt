package com.example.learnplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SectionAdapter(
    private val sectionItems: List<SectionItem>,
    private val listener: OnButtonClickListener
) : RecyclerView.Adapter<SectionAdapter.SectionViewHolder>() {

    private val buttonIds = listOf(
        R.id.map_but_1,
        R.id.map_but_2,
        R.id.map_but_3,
        R.id.map_but_4,
        R.id.map_but_5,
        R.id.map_but_6,
        R.id.map_but_7,
        R.id.map_but_8,
        R.id.map_but_9
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_section, parent, false)
        return SectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val sectionItem = sectionItems[position]
        holder.textViewSection.text = sectionItem.sectionName
        holder.textViewSubsection.text = sectionItem.description
        for (i in buttonIds.indices) {
            if (i != 3 && i != 7){
                holder.buttons[i].setImageResource(sectionItem.buttSkin)
            }

        }

        for (i in buttonIds.indices) {
            holder.buttons[i].setOnClickListener {
                listener.onButtonClick(position, (i + 1).toString())
            }
        }

        holder.button.setOnClickListener {
            listener.onButtonClick(position, "What") // Передаем номер раздела и номер кнопки (в данном случае подраздела)
        }
    }

    override fun getItemCount(): Int {
        return sectionItems.size
    }

    inner class SectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewSection: TextView = itemView.findViewById(R.id.textViewSection)
        val textViewSubsection: TextView = itemView.findViewById(R.id.textViewSubsection)
        val button: Button = itemView.findViewById(R.id.button)
        val buttons: List<ImageButton> = buttonIds.map { id ->
            itemView.findViewById(id)
        }
    }

    interface OnButtonClickListener {
        fun onButtonClick(sectionPosition: Int, buttonPosition: String)
    }

}

data class SectionItem(val sectionName: String, val description: String, val buttSkin: Int)

