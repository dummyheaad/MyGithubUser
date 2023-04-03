package com.example.mygithubuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FollowAdapter(private val listFollow: List<ItemsItem>) : RecyclerView.Adapter<FollowAdapter.ViewHolder>(){

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_follow, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val avatar = listFollow[position].avatarUrl
        val username = listFollow[position].login
        Glide.with(viewHolder.itemView.context)
            .load(avatar)
            .circleCrop()
            .into(viewHolder.ivAvatar)
        viewHolder.tvUsername.text = username
    }

    override fun getItemCount(): Int = listFollow.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivAvatar: ImageView = view.findViewById(R.id.iv_avatar_follow)
        val tvUsername: TextView = view.findViewById(R.id.tv_username_follow)
    }
}