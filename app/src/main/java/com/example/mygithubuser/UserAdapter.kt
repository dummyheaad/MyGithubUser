package com.example.mygithubuser

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(private val listUser: List<ItemsItem>) : RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_user, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val avatar = listUser[position].avatarUrl
        val username = listUser[position].login
        Glide.with(viewHolder.itemView.context)
            .load(avatar)
            .circleCrop()
            .into(viewHolder.ivAvatar)
        viewHolder.tvUsername.text = username

        viewHolder.itemView.setOnClickListener {
            // jalankan intent
            val dataIntent = Intent(viewHolder.itemView.context, DetailActivity::class.java)
            dataIntent.putExtra("username", listUser[viewHolder.adapterPosition].login)
            viewHolder.itemView.context.startActivity(dataIntent)
        }
    }

    override fun getItemCount(): Int = listUser.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivAvatar: ImageView = view.findViewById(R.id.iv_avatar)
        val tvUsername: TextView = view.findViewById(R.id.tv_username)
    }

}