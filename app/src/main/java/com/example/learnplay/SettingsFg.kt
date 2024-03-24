package com.example.learnplay

import android.annotation.SuppressLint
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

class SettingsFg : Fragment() {

    companion object {
        fun newInstance() = SettingsFg()
    }

    private lateinit var viewModel: SettingsFgViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings_fg, container, false)

        val db = DbHelper(requireContext(),null)

        val user = db.getLogUser()

        if (user != null){
            val button : Button = view.findViewById(R.id.ch_button)
            button.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Login: ${user.login}\nEmail: ${user.email}\nPass: ${user.pass}",
                    Toast.LENGTH_LONG
                ).show()
            }

            val lgOutButton : Button = view.findViewById(R.id.lg_out_button)

            lgOutButton.setOnClickListener {

                db.LogUser(user.login,"False")
                db.close()

                val intent = Intent(requireContext(),MainActivity::class.java)
                startActivity(intent)
            }
        }
        return view
    }



}