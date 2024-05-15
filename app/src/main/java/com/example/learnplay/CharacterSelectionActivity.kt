package com.example.learnplay

// CharacterSelectionActivity.kt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.learnplay.activities.MainProfile
import com.example.learnplay.adapters.CharacterPagerAdapter

class CharacterSelectionActivity : AppCompatActivity() {

    private lateinit var viewPagerCharacter: ViewPager2
    private lateinit var buttonSelectCharacter: Button
    private lateinit var characterPagerAdapter: CharacterPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_selection)

        viewPagerCharacter = findViewById(R.id.viewPagerCharacter)
        buttonSelectCharacter = findViewById(R.id.buttonSelectCharacter)

        val db = DbHelper(this,null)
        val user = db.getLogUser()

        // Создание списка персонажей
        val characterList = createCharacterList()

        // Создание и настройка адаптера
        characterPagerAdapter = CharacterPagerAdapter(characterList)
        viewPagerCharacter.adapter = characterPagerAdapter

        buttonSelectCharacter.setOnClickListener {
            // Получение выбранного персонажа из адаптера
            val selectedCharacter = characterPagerAdapter.getSelectedCharacter(viewPagerCharacter.currentItem)
            // Обработка выбранного персонажа
            // Например, вы можете передать его ID в drawable
            selectedCharacter?.let { character ->
                val selectedCharacterId = character.imageResId
                if (user != null) {
                    user.character = selectedCharacterId.toString()
                    db.updateUser(user)
                }
                Log.d("CharSelct","Name $selectedCharacterId")
                val intent = Intent(this, MainProfile::class.java)
                startActivity(intent)
            }
        }
    }

    // Метод для создания списка персонажей
    private fun createCharacterList(): List<Character> {
        // Ваша логика создания списка персонажей
        // Здесь просто пример для наглядности
        return listOf(
            Character(R.drawable.ic_archer_1, "Character 1"),
            Character(R.drawable.ic_archer_2, "Character 2"),
            Character(R.drawable.ic_archer_3, "Character 3")
        )
    }
}

data class Character(val imageResId: Int, val name: String)




