package com.example.learnplay

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast

class Learning : Fragment() {

    companion object {
        fun newInstance() = Learning()
    }

    private lateinit var viewModel: LearningViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_learning, container, false)

        val mapBut1: ImageButton = view.findViewById(R.id.map_but_1)
        val mapBut2: ImageButton = view.findViewById(R.id.map_but_2)
        val mapBut3: ImageButton = view.findViewById(R.id.map_but_3)
        val mapBut4: ImageButton = view.findViewById(R.id.map_but_4)
        val mapBut5: ImageButton = view.findViewById(R.id.map_but_5)
        val mapBut6: ImageButton = view.findViewById(R.id.map_but_6)
        val mapBut7: ImageButton = view.findViewById(R.id.map_but_7)
        val mapBut8: ImageButton = view.findViewById(R.id.map_but_8)
        val mapBut9: ImageButton = view.findViewById(R.id.map_but_9)
        val mapBut10:ImageButton = view.findViewById(R.id.map_but_10)
        val mapBut11:ImageButton = view.findViewById(R.id.map_but_11)
        val mapBut12:ImageButton = view.findViewById(R.id.map_but_12)
        val mapBut13:ImageButton = view.findViewById(R.id.map_but_13)
        val mapBut14:ImageButton = view.findViewById(R.id.map_but_14)
        val mapBoss1:ImageButton = view.findViewById(R.id.map_boss_1)
        val mapBoss2:ImageButton = view.findViewById(R.id.map_boss_2)
        val mapBoss3:ImageButton = view.findViewById(R.id.map_boss_3)
        val mapBoss4:ImageButton = view.findViewById(R.id.map_boss_4)

        val mapButtons = listOf<ImageButton>(
            mapBut1, mapBut2, mapBut3, mapBut4, mapBut5, mapBut6, mapBut7,
            mapBut8, mapBut9, mapBut10, mapBut11, mapBut12, mapBut13, mapBut14,
            mapBoss1, mapBoss2, mapBoss3, mapBoss4
        )

        val buttonTypesMap = mapOf(
            R.id.map_but_1 to Pair("Label_1", 1),
            R.id.map_but_2 to Pair("Label_1", 2),
            R.id.map_but_3 to Pair("Label_1", 3),
            R.id.map_boss_1 to Pair("Label_1", 4),
            R.id.map_but_4 to Pair("Label_1", 5),
            R.id.map_but_5 to Pair("Label_1", 6),
            R.id.map_but_6 to Pair("Label_1", 7),
            R.id.map_boss_2 to Pair("Label_1", 8),
            R.id.map_but_7 to Pair("Label_1", 9),

            R.id.map_but_8 to Pair("Label_2", 1),
            R.id.map_but_9 to Pair("Label_2", 2),
            R.id.map_but_10 to Pair("Label_2", 3),
            R.id.map_boss_3 to Pair("Label_2", 4),
            R.id.map_but_11 to Pair("Label_2", 5),
            R.id.map_but_12 to Pair("Label_2", 6),
            R.id.map_but_13 to Pair("Label_2", 7),
            R.id.map_boss_4 to Pair("Label_2", 8),
            R.id.map_but_14 to Pair("Label_2", 9),




        )


        val buttonClickListener = View.OnClickListener { view ->
            val buttonId = view.id
            val buttonInfo = buttonTypesMap[buttonId]
            buttonInfo?.let { info ->
                val (type, number) = info
                Log.d("ButtonClicked", "Clicked button Type: $type, Number: $number")
                Toast.makeText(requireContext(),"Clicked button Type: $type, Number: $number",Toast.LENGTH_SHORT).show()
            }
        }


        mapButtons.forEach { button ->
            button.setOnClickListener(buttonClickListener)
        }






        return view
    }



}