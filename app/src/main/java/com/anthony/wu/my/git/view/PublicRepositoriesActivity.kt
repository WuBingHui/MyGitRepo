package com.anthony.wu.my.git.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anthony.wu.my.git.R
import com.anthony.wu.my.git.adapter.RepositoriesAdapter
import com.anthony.wu.my.git.base.BaseActivity
import com.anthony.wu.my.git.dto.Status
import com.anthony.wu.my.git.viewmodel.GitViewModel
import com.anthony.wu.my.git.widget.CustomLoadingDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_public_repositories.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PublicRepositoriesActivity : BaseActivity() {

    companion object{
        private const val USER_NAME = "userName"

    }

    private var repositoriesAdapter: RepositoriesAdapter? = null

    private val viewModel by viewModel<GitViewModel>()

    private var customLoadingDialog:CustomLoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_public_repositories)


        initView()

        initViewModel()

        val intent = intent

        val userName = intent.getStringExtra(USER_NAME)

        customLoadingDialog?.show(supportFragmentManager, customLoadingDialog!!.tag)

        userName?.let {

            viewModel.getRespos(userName)

        }

    }



    private fun  initView(){

        customLoadingDialog = CustomLoadingDialog.newInstance()

        repositoriesAdapter = RepositoriesAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        repositoriesRecyclerView.layoutManager = linearLayoutManager
        repositoriesRecyclerView.adapter = repositoriesAdapter

    }

    private fun initViewModel(){

        viewModel.onRespos.observe(this, Observer {
            dto ->

            when (dto.status) {
                Status.SUCCESS -> {
                    dto.data?.let {
                        repositoriesAdapter?.update(it)

                        userName.setText(it[0].owner.login)

                        Glide.with(this).load(it[0].owner.avatar_url)
                            .apply(RequestOptions.bitmapTransform(CircleCrop()))
                            .placeholder(R.drawable.git_icon)
                            .into(userIcon)

                    }
                }
                Status.ERROR -> {
                    Toast.makeText(this, dto.message, Toast.LENGTH_SHORT).show()
                }
            }
            customLoadingDialog?.dismissAllowingStateLoss()

        })

    }

}
