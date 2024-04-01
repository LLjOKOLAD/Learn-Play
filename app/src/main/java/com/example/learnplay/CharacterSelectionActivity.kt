package com.example.learnplay

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import androidx.viewpager2.widget.ViewPager2

class CharacterSelectionActivity : AppCompatActivity() {

    private lateinit var viewPagerCharacter: ViewPager2
    private val characters = listOf(R.drawable.ic_archer_1, R.drawable.ic_archer_2, R.drawable.ic_archer_3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_selection)

        viewPagerCharacter = findViewById(R.id.viewPagerCharacter)
        viewPagerCharacter.adapter = CharacterAdapter(characters)
        viewPagerCharacter.orientation = ViewPager2.ORIENTATION_HORIZONTAL


        val db = DbHelper(this,null)
        val user = db.getLogUser()

        val buttonSelect: Button = findViewById(R.id.buttonSelect)
        buttonSelect.setOnClickListener {
            if (user != null) {
                user.character = "1"
                db.updateUser(user)
            }
            Toast.makeText(this,"Name: $Char", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainProfile::class.java)
            startActivity(intent)
        }
    }



}

