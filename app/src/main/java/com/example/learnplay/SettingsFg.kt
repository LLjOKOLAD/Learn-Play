package com.example.learnplay



import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.autofill.AutofillValue
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.zanvent.mathview.MathView








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

        if (user != null) {
            val button: Button = view.findViewById(R.id.ch_button)
            button.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Login: ${user.login}\nEmail: ${user.email}\nPass: ${user.pass}",
                    Toast.LENGTH_LONG
                ).show()
            }

            val lgOutButton: Button = view.findViewById(R.id.lg_out_button)

            lgOutButton.setOnClickListener {

                db.LogUser(user.email, "False")
                db.close()

                val intent = Intent(requireContext(), MainActivity::class.java)
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
                if (exp < 50) {
                    exp = 0
                } else {
                    exp -= 50
                }
                user.experience = exp
                db.updateUser(user)
            }

            val stButton: Button = view.findViewById(R.id.startTasks)

            stButton.setOnClickListener {
                val intent = Intent(requireContext(), TasksChallenging::class.java)
                startActivity(intent)
            }

            val pgButton: Button = view.findViewById(R.id.startPing)

            pgButton.setOnClickListener {
                val intent = Intent(requireContext(), ServerPinging::class.java)
                startActivity(intent)
            }




            val mathview: MathView = view.findViewById(R.id.mathView1)
            mathview.text = "<p>Найдите значение выражения: \$2,7/(1- 4/13)\$</p>"
            mathview.pixelScaleType = MathView.Scale.SCALE_DP
            mathview.setTextSize(16)
            mathview.textColor = "#111111"




        }







        db.close()

        return view
    }



}