package com.anthony.wu.my.git.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.anthony.wu.my.git.R
import com.anthony.wu.my.git.adapter.RepositoriesAdapter
import com.anthony.wu.my.git.adapter.UserAdapter
import com.anthony.wu.my.git.base.BaseActivity
import com.anthony.wu.my.git.dto.Status
import com.anthony.wu.my.git.viewmodel.GitViewModel
import com.anthony.wu.my.git.widget.CustomLoadingDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_public_repositories.*
import kotlinx.android.synthetic.main.activity_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : BaseActivity() {

    private var userAdapter: UserAdapter? = null

    private val viewModel by viewModel<GitViewModel>()


    private var customLoadingDialog: CustomLoadingDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initView()

        initViewModel()



    }

    private fun  initView(){

        customLoadingDialog = CustomLoadingDialog.newInstance()

        userAdapter = UserAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        usersRecyclerView.layoutManager = linearLayoutManager
        usersRecyclerView.adapter = userAdapter

        searchImg.setOnClickListener {

            if(searchUserEditText.text.toString().isNotEmpty()){

                customLoadingDialog?.show(supportFragmentManager, customLoadingDialog!!.tag)

                viewModel.getUserList(searchUserEditText.text.toString())

            }else{

                Toast.makeText(this,getString(R.string.not_empty), Toast.LENGTH_SHORT).show()
            }

        }

    }

    private fun initViewModel(){

        viewModel.onUserList.observe(this, Observer {
                dto ->

            when (dto.status) {
                Status.SUCCESS -> {

                    dto.data?.let {

                        if(it.items.isNotEmpty()){
                            usersRecyclerView.visibility = View.VISIBLE
                            noUserDataLabel.visibility = View.GONE
                            userAdapter?.update(it.items)
                        }else{
                            usersRecyclerView.visibility = View.GONE
                            noUserDataLabel.visibility = View.VISIBLE
                        }

                    }

                }
                Status.ERROR -> {
                    usersRecyclerView.visibility = View.GONE
                    noUserDataLabel.visibility = View.VISIBLE
                    Toast.makeText(this, dto.message, Toast.LENGTH_SHORT).show()
                }
            }

            customLoadingDialog?.dismissAllowingStateLoss()

        })

    }
}
