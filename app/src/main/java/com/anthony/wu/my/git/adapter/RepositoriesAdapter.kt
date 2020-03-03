package com.anthony.wu.my.git.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anthony.wu.my.git.R
import com.anthony.wu.my.git.dto.response.ResposDto
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions


class RepositoriesAdapter(private val context: Context) :
    RecyclerView.Adapter<RepositoriesAdapter.ViewHolder>() {

    private var repositoryList = listOf<ResposDto>()

    fun update(repositoryList: List<ResposDto>) {

        this.repositoryList = repositoryList

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_repositories,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {

        return repositoryList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = repositoryList[position]

        Glide.with(context).load(data.owner.avatar_url)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .placeholder(R.drawable.git_icon)
            .into(holder.userIcon)

        holder.repositoryStar.text = data.stargazers_count.toString()

        holder.repositoryBranch.text = data.forks_count.toString()

        holder.repositoryOwner.text = data.stargazers_count.toString()

        holder.repositoryName.text = data.name

        data.description?.let {
            holder.repositoryDescription.text = it
        }

        holder.repositoryLanguage.text = data.language

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val userIcon = view.findViewById<ImageView>(R.id.userIcon)
        val repositoryName = view.findViewById<TextView>(R.id.repositoryName)
        val repositoryDescription = view.findViewById<TextView>(R.id.repositoryDescription)
        val repositoryStar = view.findViewById<TextView>(R.id.repositoryStar)
        val repositoryBranch = view.findViewById<TextView>(R.id.repositoryBranch)
        val repositoryOwner = view.findViewById<TextView>(R.id.repositoryOwner)
        val repositoryLanguage = view.findViewById<TextView>(R.id.repositoryLanguage)

    }


}