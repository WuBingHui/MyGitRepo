package com.anthony.wu.my.git.view

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.anthony.wu.my.git.R
import com.anthony.wu.my.git.base.BaseFragment
import com.anthony.wu.my.git.viewmodel.GitViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class CollaboratorsFragment :BaseFragment(){

    private val viewModel by viewModel<GitViewModel>()

    companion object {

        @JvmStatic
        fun newInstance() =
            CollaboratorsFragment().apply {
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
        return inflater.inflate(R.layout.fragment_collaborators, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }




}