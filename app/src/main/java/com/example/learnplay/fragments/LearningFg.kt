package com.example.learnplay.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learnplay.viewModels.LearningViewModel
import com.example.learnplay.R
import com.example.learnplay.adapters.SectionAdapter
import com.example.learnplay.adapters.SectionItem

class LearningFg : Fragment() {

    companion object {
        fun newInstance() = LearningFg()
    }

    private lateinit var viewModel: LearningViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { val view = inflater.inflate(R.layout.fragment_learning_fg, container, false)



        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewLearning)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager




        val sectionItems = mutableListOf(
            SectionItem("Раздел 1", "Клартиры и садовые участки", R.drawable.map_reg_button,5),
            SectionItem("Раздел 2", "Автомобили", R.drawable.map_reg_dess_button,5),
            SectionItem("Раздел 3", "Компьютеры", R.drawable.map_reg_dess_button,1),
            SectionItem("Раздел 4", "Телефоны", R.drawable.map_reg_button,0)
        )

        val adapter = SectionAdapter(sectionItems, object : SectionAdapter.OnButtonClickListener {
            override fun onButtonClick(sectionPosition: Int, buttonPosition: String) {
                // Обработка нажатия на кнопку
                Toast.makeText(requireContext(),"Нажата кнопка в разделе $sectionPosition\nКнопка: $buttonPosition",Toast.LENGTH_SHORT).show()

            }
        })


        recyclerView.adapter = adapter



        return view
    }





}