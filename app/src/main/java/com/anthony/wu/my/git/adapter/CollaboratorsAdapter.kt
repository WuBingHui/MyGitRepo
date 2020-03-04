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
import com.anthony.wu.my.git.dto.response.CollaboratorsDto
import com.anthony.wu.my.git.dto.response.CommitsDto
import com.anthony.wu.my.git.dto.response.Item
import com.anthony.wu.my.git.dto.response.ResposDto
import com.anthony.wu.my.git.view.PublicRepositoriesActivity
import com.anthony.wu.my.git.view.SearchActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import org.w3c.dom.Text


class CollaboratorsAdapter(private val context: Context) :
    RecyclerView.Adapter<CollaboratorsAdapter.ViewHolder>() {

    private var collaboratorsList = listOf<CollaboratorsDto>()

    fun update(collaboratorsList: List<CollaboratorsDto>) {

        this.collaboratorsList = collaboratorsList

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_collaborator,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {

        return collaboratorsList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = collaboratorsList[position]

        Glide.with(context).load(data.avatar_url)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .placeholder(R.drawable.git_icon)
            .into(holder.collaboratorImg)

        holder.collaboratorName.text = data.login

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val collaboratorImg = view.findViewById<ImageView>(R.id.collaboratorImg)
        val collaboratorName = view.findViewById<TextView>(R.id.collaboratorName)


    }


}