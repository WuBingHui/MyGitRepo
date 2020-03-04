package com.anthony.wu.my.git.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.anthony.wu.my.git.R
import com.anthony.wu.my.git.dto.response.CommitsDto


class CommitsAdapter() :
    RecyclerView.Adapter<CommitsAdapter.ViewHolder>() {

    private var commitsList = listOf<CommitsDto>()

    fun update(commitsList: List<CommitsDto>) {

        this.commitsList = commitsList

        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_commit,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {

        return commitsList.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = commitsList[position]


        holder.userName.text = data.commit.committer.name

        holder.date.text = data.commit.committer.date

        holder.message.text = data.commit.message


    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val userName = view.findViewById<TextView>(R.id.userName)
        val date = view.findViewById<TextView>(R.id.date)
        val message = view.findViewById<TextView>(R.id.message)

    }


}