package com.anthony.wu.my.git.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewStub
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anthony.wu.my.git.R
import com.anthony.wu.my.git.adapter.CommitsAdapter
import com.anthony.wu.my.git.adapter.RepositoriesAdapter
import com.anthony.wu.my.git.base.BaseFragment
import com.anthony.wu.my.git.dto.Status
import com.anthony.wu.my.git.viewmodel.GitViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_public_repositories.*
import kotlinx.android.synthetic.main.fragment_commits.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CommitsFragment  : BaseFragment(){

    private val viewModel by viewModel<GitViewModel>()

    private var  commitsAdapter:CommitsAdapter? = null
    private var userName = ""

    private var repo = ""

    companion object {


        @JvmStatic
        fun newInstance(userName:String,repo:String) =
            CommitsFragment().apply {

                this.userName = userName
                this.repo = repo

                arguments = Bundle().apply {


                }
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_commits, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        initViewModel()

        viewModel.getCommits(userName,repo)

    }

    private fun initView(){

        context?.let {
            commitsAdapter = CommitsAdapter(it)
            val linearLayoutManager = LinearLayoutManager(it)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            commitsRecyclerView.layoutManager = linearLayoutManager
            commitsRecyclerView.adapter = commitsAdapter
        }



    }

    private fun initViewModel(){

        viewModel.onCommits.observe(viewLifecycleOwner, Observer {
            dto ->
            when (dto.status) {
                Status.SUCCESS -> {

                    dto.data?.let {
                        commitsAdapter?.update(it)
                    }

                }
                Status.ERROR -> {
                    Toast.makeText(context, dto.message, Toast.LENGTH_SHORT).show()
                }
            }
        })

    }



}