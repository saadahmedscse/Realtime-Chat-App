package com.caffeine.eirmon.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.caffeine.eirmon.databinding.ItemUserLayoutBinding
import com.caffeine.eirmon.service.model.UserModel
import com.caffeine.eirmon.util.Constants
import com.squareup.picasso.Picasso

class UserAdapter(val list : ArrayList<UserModel>, val context : Context) : RecyclerView.Adapter<UserAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemUserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = list[position]
        holder.name.text = user.name
        if (user.picture != "null"){
            Picasso.get().load(user.picture).into(holder.profile)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(binding : ItemUserLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.name
        val profile = binding.profilePicture
    }
}