package com.anthony.wu.my.git.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anthony.wu.my.git.R
import com.anthony.wu.my.git.adapter.RepositoriesAdapter
import com.anthony.wu.my.git.adapter.UserAdapter
import com.anthony.wu.my.git.base.BaseActivity
import com.anthony.wu.my.git.dto.Status
import com.anthony.wu.my.git.extension.addTo
import com.anthony.wu.my.git.utils.SharedPreferencesUtils
import com.anthony.wu.my.git.viewmodel.GitViewModel
import com.anthony.wu.my.git.widget.CustomLoadingDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_public_repositories.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class PublicRepositoriesActivity : BaseActivity() {

    companion object {
        private const val USER_NAME = "userName"

    }

    private var exitTime: Long = 0

    private lateinit var sharedPreferencesUtils: SharedPreferencesUtils

    private var repositoriesAdapter: RepositoriesAdapter? = null

    private val viewModel by viewModel<GitViewModel>()

    private var customLoadingDialog: CustomLoadingDialog? = null

    private var  searchDialogFragment:SearchDialogFragment? = null

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


    private fun initView() {

        sharedPreferencesUtils = SharedPreferencesUtils(this)

        customLoadingDialog = CustomLoadingDialog.newInstance()

        repositoriesAdapter = RepositoriesAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        repositoriesRecyclerView.layoutManager = linearLayoutManager
        repositoriesRecyclerView.adapter = repositoriesAdapter

        searchOtherUser.setOnClickListener {

            searchDialogFragment = SearchDialogFragment.newInstance()

            searchDialogFragment!!.show(supportFragmentManager, searchDialogFragment!!.tag)

            initSubject()

        }

        logout.setOnClickListener {

            sharedPreferencesUtils.clearAll()

            val intent = Intent()
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            intent.setClass(this, LoginActivity::class.java)
            startActivity(intent)

        }

        sharedPreferencesUtils.getAuthorization()?.let {

            logout.visibility = if (it.isEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }

        }?: kotlin.run {
            logout.visibility = View.GONE
        }

    }

    private fun initViewModel() {

        viewModel.onRespos.observe(this, Observer { dto ->

            when (dto.status) {
                Status.SUCCESS -> {
                    dto.data?.let {
                        repositoriesAdapter?.update(it)

                        userName.text = it[0].owner.login

                        Glide.with(this).load(it[0].owner.avatar_url)
                            .apply(RequestOptions.bitmapTransform(CircleCrop()))
                            .placeholder(R.drawable.git_icon)
                            .into(userIcon)

                    }!!
                }
                Status.ERROR -> {
                    Toast.makeText(this, dto.message, Toast.LENGTH_SHORT).show()
                }
            }
            customLoadingDialog?.dismissAllowingStateLoss()

        })

    }

    private fun initSubject(){

        searchDialogFragment?.getUserCallBack()?.subscribe { user ->

            customLoadingDialog?.show(supportFragmentManager, customLoadingDialog!!.tag)

            viewModel.getRespos(user)

        }?.addTo(compositeDisposable)

    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(this, getString(R.string.exit_app), Toast.LENGTH_SHORT).show()
            exitTime = System.currentTimeMillis()
        } else {
            moveTaskToBack(true)

        }
    }

}
