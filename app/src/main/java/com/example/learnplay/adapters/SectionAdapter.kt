package com.example.learnplay.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnplay.R

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
        R.id.map_but_5
    )

    private val buttonIdsnum = listOf(
        R.id.map_but_1_num,
        R.id.map_but_2_num,
        R.id.map_but_3_num,
        R.id.map_but_3_num,
        R.id.map_but_4_num,
        R.id.map_but_5_num
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_section, parent, false)
        return SectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val sectionItem = sectionItems[position]
        holder.textViewSection.text = sectionItem.sectionName
        holder.textViewSubsection.text = sectionItem.description
        val step = sectionItem.step - 1

        for (i in buttonIds.indices) {
            if(i <= step){
                holder.buttons[i].setImageResource(sectionItem.buttSkin)
            }
            else{
                holder.buttons[i].setImageResource(R.drawable.ic_reg_disable_but)
                holder.buttons[i].isClickable = false
                holder.buttonsnum[i].visibility = View.INVISIBLE
            }


        }

        for (i in buttonIds.indices) {
            val step = sectionItem.step - 1
            if (i <= step) {
                holder.buttons[i].setOnClickListener {
                    listener.onButtonClick(position, (i + 1).toString())
                }
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
        val buttonsnum :List<ImageView> = buttonIdsnum.map { id ->
            itemView.findViewById(id)
        }
    }

    interface OnButtonClickListener {
        fun onButtonClick(sectionPosition: Int, buttonPosition: String)
    }

}

data class SectionItem(val sectionName: String, val description: String, val buttSkin: Int,val step: Int)

