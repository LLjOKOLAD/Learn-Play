package com.example.learnplay.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.learnplay.R
import com.example.learnplay.dataClasses.UserRating

class UserAdapter(private val users: List<UserRating>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userNameTextView: TextView = itemView.findViewById(R.id.userNameTextView)
        val rankPlaceTextView: TextView = itemView.findViewById(R.id.rankPlaceTextView)
        val expTextView: TextView = itemView.findViewById(R.id.expTextView)
        val ratingIcon: ImageView = itemView.findViewById(R.id.ratingIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        holder.userNameTextView.text = user.userName
        holder.rankPlaceTextView.text = user.rank_place.toString()
        holder.expTextView.text = user.exp.toString() + " exp"

        when(user.rank_place){
            1 ->{ holder.ratingIcon.setImageResource(R.drawable.ic_rank_gold)}
            2 ->{ holder.ratingIcon.setImageResource(R.drawable.ic_rank_silver)}
            3 ->{ holder.ratingIcon.setImageResource(R.drawable.ic_rank_bronze)}
        }

    }

    override fun getItemCount() = users.size
}