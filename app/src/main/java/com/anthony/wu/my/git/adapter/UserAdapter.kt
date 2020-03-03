package com.anthony.wu.my.git.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anthony.wu.my.git.R
import com.anthony.wu.my.git.dto.response.Item
import com.anthony.wu.my.git.dto.response.ResposDto
import com.anthony.wu.my.git.view.PublicRepositoriesActivity
import com.anthony.wu.my.git.view.SearchActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions


class UserAdapter(private val context: Context) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    companion object{
        private const val USER_NAME = "userName"

    }

    private var userList = listOf<Item>()

    fun update(userList: List<Item>) {

        this.userList = userList

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_user_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {

        return userList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = userList[position]

        Glide.with(context).load(data.avatar_url)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .placeholder(R.drawable.git_icon)
            .into(holder.userIcon)

        holder.userName.text = data.login

        holder.itemView.setOnClickListener {

            val intent = Intent()
            intent.putExtra(USER_NAME,data.login)
            intent.setClass(context, PublicRepositoriesActivity::class.java)
            context.startActivity(intent)

        }

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val userIcon = view.findViewById<ImageView>(R.id.userIcon)
        val userName = view.findViewById<TextView>(R.id.userName)


    }


}