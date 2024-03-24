package com.example.learnplay

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

class ProfileFg : Fragment() {

    companion object {
        fun newInstance() = ProfileFg()

    }

    private lateinit var viewModel: ProfileFgViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_fg, container, false)



    }


}