package com.example.learnplay

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CharacterPagerAdapter(private val characterList: List<Character>) :
    RecyclerView.Adapter<CharacterPagerAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Объявите представления элемента здесь, например:
         val imageViewCharacter: ImageView = itemView.findViewById(R.id.imageViewCharacter)
         val textViewCharacterName: TextView = itemView.findViewById(R.id.textViewCharacterName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character_selection, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        // Установите данные персонажа для представлений элемента
        val character = characterList[position]
        // Например:
        holder.imageViewCharacter.setImageResource(character.imageResId)
        holder.textViewCharacterName.text = character.name
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    // Метод для получения выбранного персонажа
    fun getSelectedCharacter(position: Int): Character? {
        return if (position in 0 until characterList.size) {
            characterList[position]
        } else {
            null
        }
    }
}
