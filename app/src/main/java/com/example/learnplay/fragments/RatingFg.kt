package com.example.learnplay.fragments

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learnplay.ApiClient
import com.example.learnplay.R
import com.example.learnplay.adapters.UserAdapter
import com.example.learnplay.dataClasses.TaskResponse
import com.example.learnplay.dataClasses.UserRating
import com.example.learnplay.viewModels.RatingViewModel
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingFg : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter

    private lateinit var userRank: TextView
    private lateinit var userRankIcon: ImageView
    private lateinit var userName: TextView
    private lateinit var userExp: TextView

    companion object {
        fun newInstance() = RatingFg()
    }

    private val viewModel: RatingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_rating_fg, container, false)

        recyclerView = view.findViewById(R.id.recyclerViewRating)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        userRank = view.findViewById(R.id.rankPlaceTextView)
        userRankIcon = view.findViewById(R.id.ratingIcon)
        userName = view.findViewById(R.id.userNameTextView)
        userExp = view.findViewById(R.id.expTextView)





        getUsersRating()

        getUserRating()

        return view
    }

    private fun getUsersRating(){
        val call = ApiClient.apiService.getUsers()

        call.enqueue(object : Callback<List<UserRating>> {
            override fun onResponse(call: Call<List<UserRating>>, response: Response<List<UserRating>>) {
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    userResponse?.let{
                        userAdapter = UserAdapter(it)
                        recyclerView.adapter = userAdapter
                    }
                } else {
                    Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<UserRating>>, t: Throwable) {
                Toast.makeText(requireContext(), "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("Error","${t.message}")
            }
        })
    }

    private fun getUserRating(){
        val call = ApiClient.apiService.getUser()

        call.enqueue(object : Callback<UserRating> {
            override fun onResponse(call: Call<UserRating>, response: Response<UserRating>) {
                if (response.isSuccessful) {
                    val userResponse = response.body()
                    userResponse?.let{

                        userRank.text = it.rank_place.toString()
                        when(it.rank_place){
                            1 -> {userRankIcon.setImageResource(R.drawable.ic_rank_gold)}
                            2 -> {userRankIcon.setImageResource(R.drawable.ic_rank_silver)}
                            3 -> {userRankIcon.setImageResource(R.drawable.ic_rank_bronze)}
                        }
                        userName.text = it.userName
                        userExp.text = it.exp.toString() + " exp"


                    }
                } else {
                    Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<UserRating>, t: Throwable) {
                Toast.makeText(requireContext(), "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.e("Error","${t.message}")
            }
        })
    }


}