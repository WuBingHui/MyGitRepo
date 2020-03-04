package com.anthony.wu.my.git.view

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anthony.wu.my.git.R
import com.anthony.wu.my.git.adapter.CollaboratorsAdapter
import com.anthony.wu.my.git.adapter.CommitsAdapter
import com.anthony.wu.my.git.base.BaseFragment
import com.anthony.wu.my.git.dto.Status
import com.anthony.wu.my.git.utils.SharedPreferencesUtils
import com.anthony.wu.my.git.viewmodel.GitViewModel
import kotlinx.android.synthetic.main.fragment_collaborators.*
import kotlinx.android.synthetic.main.fragment_commits.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class CollaboratorsFragment : BaseFragment() {

    private val viewModel by viewModel<GitViewModel>()
    private var userName = ""
    private lateinit var sharedPreferencesUtils: SharedPreferencesUtils
    private var repo = ""
    private var collaboratorsAdapter: CollaboratorsAdapter? = null

    companion object {

        @JvmStatic
        fun newInstance(userName: String, repo: String) =
            CollaboratorsFragment().apply {

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_collaborators, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        initViewModel()

        sharedPreferencesUtils.getAuthorization()?.let {

            if(it.isNotEmpty()){
                viewModel.getCollaborators(it,userName, repo)
            }

        }

    }

    private fun initView() {

        context?.let {

            sharedPreferencesUtils = SharedPreferencesUtils(it)

            collaboratorsAdapter = CollaboratorsAdapter(it)
            val linearLayoutManager = LinearLayoutManager(it)
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            collaboratorsRecyclerView.layoutManager = linearLayoutManager
            collaboratorsRecyclerView.adapter = collaboratorsAdapter

        }

    }

    private fun initViewModel() {

        viewModel.onCollaborators.observe(viewLifecycleOwner, Observer { dto ->
            when (dto.status) {
                Status.SUCCESS -> {

                    dto.data?.let {
                        collaboratorsAdapter?.update(it)
                    }

                }
                Status.ERROR -> {
                    Toast.makeText(context, dto.message, Toast.LENGTH_SHORT).show()
                }
            }
        })


    }


}