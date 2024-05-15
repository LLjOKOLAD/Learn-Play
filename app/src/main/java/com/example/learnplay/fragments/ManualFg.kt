package com.example.learnplay.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.example.learnplay.viewModels.ManualViewModel
import com.example.learnplay.R
import com.example.learnplay.adapters.ThemeAdapter

class ManualFg : Fragment() {

    companion object {
        fun newInstance() = ManualFg()
    }

    private val viewModel: ManualViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View { val view = inflater.inflate(R.layout.fragment_manual, container, false)

        val listView = view.findViewById<ListView>(R.id.listView)

        // Пример данных для списка
        val themes = listOf("Вероятность и статистика", "Алгебраические выражения. Степени и корни", "Theme 3", "Theme 4", "Theme 5")

        val adapter = ThemeAdapter(requireContext(), themes)
        listView.adapter = adapter


        return view
    }
}