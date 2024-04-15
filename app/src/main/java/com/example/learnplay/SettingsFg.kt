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
import com.agog.mathdisplay.MTMathView

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

            val addBut: Button = view.findViewById(R.id.add50exp)

            addBut.setOnClickListener {
                var exp = user.experience
                exp += 50
                user.experience = exp
                db.updateUser(user)
            }

            val takeBut: Button = view.findViewById(R.id.take50exp)

            takeBut.setOnClickListener {
                var exp = user.experience
                if(exp < 50){
                    exp = 0
                }
                else{
                    exp -= 50
                }
                user.experience = exp
                db.updateUser(user)
            }

            val stButton: Button = view.findViewById(R.id.startTasks)

            stButton.setOnClickListener {
                val intent = Intent(requireContext(),TasksChallenging::class.java)
                startActivity(intent)
            }

            val pgButton: Button = view.findViewById(R.id.startPing)

            pgButton.setOnClickListener{
                val intent = Intent(requireContext(),ServerPinging::class.java)
                startActivity(intent)
            }

            val mathview: MTMathView =view.findViewById(R.id.mathView)
            mathview.fontSize = 150f


            mathview.latex = "x = \\frac{-b \\pm \\sqrt{b^2-4ac}}{2a}"
            mathview.latex = "-2(x-4)=0,8"
            mathview.latex = "1)  \\frac{n}{m}>0"



        }
        db.close()

        return view
    }



}